package com.addresses.external;

import com.addresses.application.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/address")
public class Controller
 {

     private final Service service;

     public Controller(Service service) {
         this.service = service;
     }

     @PostMapping("/{zipCode}")
     public Mono<ResponseEntity<String>> getAddress(
             @PathVariable("zipCode") String zipCode,
             @RequestBody Request request
     ) {
         return service.execute(request)
                 .flatMap(it -> Mono.just(ResponseEntity.ok("Success")))
                 .onErrorResume(err -> Mono.just(ResponseEntity.internalServerError().body(err.getMessage())));
     }
 }
