package com.miniproject.miniretail.dto;

import lombok.Data;

@Data
public class Customer {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePict;
}
