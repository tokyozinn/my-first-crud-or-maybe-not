package com.addresses.external;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class Request {

    private String name;
    private String email;
    private String cpf;
    private String zipCode;
}
