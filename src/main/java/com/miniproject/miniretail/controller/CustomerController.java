package com.miniproject.miniretail.controller;

import com.miniproject.miniretail.dto.*;
import com.miniproject.miniretail.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @QueryMapping
    public Mono<CustomerInput> getCustomerById(@Argument String id){
        return this.customerService.getCustomerById(id);
    }

    @QueryMapping
    public Mono<List<CustomerInput>> getAllCustomer(){
        return this.customerService.getAllCustomer();
    }

    @MutationMapping
    public Mono<Customer> createCustomer(@Argument("input") CustomerInput customer){
        return this.customerService.createCustomer(customer);
    }

    @MutationMapping
    public Mono<Customer> login(@Argument("input") LoginInput loginInput){
        return this.customerService.login(loginInput);
    }

    @MutationMapping
    public Mono<CustomerInput> editCustomer(@Argument String id, @Argument CustomerInput customer){
        return this.customerService.editCustomer(id, customer);
    }

    @MutationMapping
    public Mono<CustomerInput> deleteCustomer(@Argument String id){
        return this.customerService.deleteCustomer(id);
    }
}
