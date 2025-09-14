package com.addresses.application;

import com.addresses.application.impl.ServiceOrchestratorImpl;
import com.addresses.core.model.Address;
import com.addresses.core.usecases.UseCase;
import com.addresses.external.OutsideAPI;
import com.addresses.external.Request;
import com.addresses.external.SensitiveDataAPI;
import com.addresses.external.persistance.Repository;
import com.addresses.external.persistance.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ServiceOrchestratorImplTest {

    @Test
    void should_process_request_and_save_in_database() {
        Repository repository = Mockito.mock(Repository.class);
        SensitiveDataAPI sensitiveDataAPI = Mockito.mock(SensitiveDataAPI.class);
        OutsideAPI outsideAPI = Mockito.mock(OutsideAPI.class);
        UseCase useCase = Mockito.mock(UseCase.class);

        ServiceOrchestrator serviceOrchestrator = ServiceOrchestratorImpl.builder().outsideAPI(outsideAPI).repository(repository).sensitiveDataAPI(sensitiveDataAPI).useCase(useCase).build();

        when(repository.save(any())).thenReturn(Mono.just(User.builder().build()));
        when(sensitiveDataAPI.getSensitiveData(any())).thenReturn(Mono.just("ok"));
        when(outsideAPI.getAddress(any())).thenReturn(Mono.just(new Address()));
        when(useCase.verify(any())).thenReturn(Mono.just(true));

        Request req = Request.builder()
                .cpf("012")
                .email("test@test")
                .name("test")
                .zipCode("12345")
                .build();

        StepVerifier.create(serviceOrchestrator.execute(req))
                .consumeNextWith(Assertions::assertNotNull)
                .verifyComplete();
    }
}
