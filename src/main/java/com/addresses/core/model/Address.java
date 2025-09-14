package com.addresses.core.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Address {

    private String state;
    private String city;
    private String neighborhood;
    private String street;
}
