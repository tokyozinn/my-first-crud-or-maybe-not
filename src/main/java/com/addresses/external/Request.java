package com.addresses.external;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Request {

    private String name;
    private String email;
    private String cpf;
    private String zipCode;
}
