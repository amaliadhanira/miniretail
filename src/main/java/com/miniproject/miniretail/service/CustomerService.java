package com.miniproject.miniretail.service;

import com.miniproject.miniretail.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {

    private final WebClient client;

    public CustomerService(@Value("${customer.serivce.url}") String url){
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<CustomerInput> getCustomerById(String id){
        return this.client.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(CustomerInput.class);
    }

    public Mono<List<CustomerInput>> getAllCustomer(){
        return this.client.get()
                .uri("/allCustomer")
                .retrieve()
                .bodyToFlux(CustomerInput.class)
                .collectList();
    }

    public Mono<Customer> createCustomer(CustomerInput customer){
        return this.client.post()
                .uri("/inputCustomer")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    public Mono<Customer> login(LoginInput loginInput){
        return this.client.post()
                .uri("/login")
                .bodyValue(loginInput)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    public Mono<CustomerInput> editCustomer(String id, CustomerInput customer){
        return this.client.put()
                .uri("/editCustomer/{id}", id)
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(CustomerInput.class);
    }

    public Mono<CustomerInput> deleteCustomer(String id){
        return this.client.delete()
                .uri("/deleteCustomer/{id}", id)
                .retrieve()
                .bodyToMono(CustomerInput.class);
    }
}
