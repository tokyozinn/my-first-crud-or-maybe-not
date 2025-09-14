package com.addresses.external;

import com.addresses.application.ServiceOrchestrator;
import com.addresses.core.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/address")
public class Controller
 {

     private final ServiceOrchestrator serviceOrchestrator;

     public Controller(ServiceOrchestrator serviceOrchestrator) {
         this.serviceOrchestrator = serviceOrchestrator;
     }

     @PostMapping("/{zipCode}")
     public Mono<ResponseEntity<Address>> getAddress(
             @PathVariable("zipCode") String zipCode,
             @RequestBody Request request
     ) {
         return serviceOrchestrator.execute(request)
                 .flatMap(it -> Mono.just(ResponseEntity.ok(it)))
                 .doOnError(throwable -> Mono.just(ResponseEntity.internalServerError().body(throwable.getMessage())));
     }
 }
