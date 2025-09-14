package com.addresses.core.usecases.impl;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
public class UseCaseImpl {

    Boolean verify(String cpf) {
        return true;
    }
}
