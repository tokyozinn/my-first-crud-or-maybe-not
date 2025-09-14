package com.addresses.application;

import com.addresses.core.model.Address;
import com.addresses.external.Request;
import reactor.core.publisher.Mono;

public interface Service {

    Mono<Address> execute(Request request);
}
