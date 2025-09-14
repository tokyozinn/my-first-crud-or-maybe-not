package com.addresses.external.persistance.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("users")
@Builder
@Data
public class User {
    @Id
    private final String id =  UUID.randomUUID().toString();
    private String cpf;
    private String name;
    private String email;
}
