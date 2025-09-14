package com.addresses.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class Address {

    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("neighborhood")
    private String neighborhood;
    @JsonProperty("street")
    private String street;
}
