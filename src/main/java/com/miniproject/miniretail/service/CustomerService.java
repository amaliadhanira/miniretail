package com.miniproject.miniretail.service;

import com.miniproject.miniretail.dto.*;
import com.miniproject.miniretail.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {

    private final WebClient client;

    @Autowired
    private JwtUtil jwtUtil;

    public CustomerService(@Value("${customer.serivce.url}") String url){
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Customer> createCustomer(CustomerInput customer){
        return this.client.post()
                .uri("/inputCustomer")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    public Mono<LoginResponse> login(LoginInput loginInput){
        return this.client.post()
                .uri("/login")
                .bodyValue(loginInput)
                .retrieve()
                .bodyToMono(Boolean.class)
                .map(valid -> {
                    if(valid){
                        String token = jwtUtil.generateToken(loginInput.getEmail());
                        return new LoginResponse(token, Status.SUCCESS);
                    }
                    return new LoginResponse(null, Status.FAILURE);
                });
    }

    public Mono<EditResponse> updateCustomer(String id, CustomerInput customer){
        return this.client.put()
                .uri("/editCustomer/{id}", id)
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(CustomerInput.class)
                .map(response ->
                        new EditResponse(Status.SUCCESS)
                )
                .onErrorResume(error ->
                        Mono.just(new EditResponse(Status.FAILURE))
                );
    }

    public Mono<DeleteResponse> deleteCustomer(String id){
        return this.client.delete()
                .uri("/deleteCustomer/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .map(response -> new DeleteResponse(id, Status.SUCCESS))
                .defaultIfEmpty(new DeleteResponse(id, Status.SUCCESS))
                .onErrorResume(error -> Mono.just(new DeleteResponse(id, Status.FAILURE)));
    }
}
