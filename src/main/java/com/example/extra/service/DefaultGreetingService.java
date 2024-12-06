package com.example.extra.service;

import jakarta.enterprise.context.*;

@ApplicationScoped
public class DefaultGreetingService implements GreetingService {

    @Override
    public String greet(String name) {
        return "Hello " + name;
    }

}
