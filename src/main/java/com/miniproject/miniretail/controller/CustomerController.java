package com.miniproject.miniretail.controller;

import com.miniproject.miniretail.dto.*;
import com.miniproject.miniretail.service.CustomerService;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @MutationMapping
    public Mono<Customer> createCustomer(@Argument("input") CustomerInput customer){
        return this.customerService.createCustomer(customer);
    }

    @MutationMapping
    public Mono<LoginResponse> login(@Argument("input") LoginInput loginInput){
        return this.customerService.login(loginInput);
    }

    @MutationMapping
    public Mono<EditResponse> updateCustomer(@Argument String id, @Argument CustomerInput customer){
        return this.customerService.updateCustomer(id, customer);
    }

    @MutationMapping
    public Mono<DeleteResponse> deleteCustomer(@Argument String id){
        return this.customerService.deleteCustomer(id);
    }
}
