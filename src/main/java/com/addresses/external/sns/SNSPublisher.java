package com.addresses.external.sns;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SNSPublisher {

    private final SnsAsyncClient sns;
    private final ObjectMapper mapper = new ObjectMapper();

    public SNSPublisher(SnsAsyncClient sns) {
        this.sns = sns;
    }

    /** Publish plain text (returns SNS messageId) */
    public Mono<String> publish(String topicArn, String subject, String message, Map<String, String> attrs) {
        PublishRequest.Builder b = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message);

        if (subject != null && !subject.isBlank()) {
            b.subject(subject);
        }
        if (attrs != null && !attrs.isEmpty()) {
            b.messageAttributes(attrs.entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> MessageAttributeValue.builder()
                            .dataType("String")
                            .stringValue(e.getValue())
                            .build()
            )));
        }

        return Mono.fromFuture(sns.publish(b.build()))
                .map(PublishResponse::messageId);
    }

    /** Publish a JSON payload */
    public Mono<String> publishJson(String topicArn, Object payload, String subject) {
        try {
            String json = mapper.writeValueAsString(payload);
            return publish(topicArn, subject, json, Map.of("content-type", "application/json"));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    /** Ensure a topic exists and return its ARN (idempotent) */
    public Mono<String> ensureTopic(String name) {
        return Mono.fromFuture(sns.createTopic(CreateTopicRequest.builder().name(name).build()))
                .map(CreateTopicResponse::topicArn);
    }
}
