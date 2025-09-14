package com.addresses.external.sns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;

import java.net.URI;

@Configuration
public class SNSConfig {

    @Bean
    SnsAsyncClient snsAsyncClient(
            @Value("${aws.region}") String region,
            @Value("${aws.sns.endpoint}") String endpoint) {

        return SnsAsyncClient.builder()
                .httpClientBuilder(NettyNioAsyncHttpClient.builder())
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))                 // remove for real AWS
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")))       // replace for real AWS
                .build();
    }
}
