package com.addresses.application;

import com.addresses.core.model.Address;
import com.addresses.external.Request;
import reactor.core.publisher.Mono;

public interface ServiceOrchestrator {

    Mono<Address> execute(Request request);
}
