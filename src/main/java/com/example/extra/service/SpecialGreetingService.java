package com.example.extra.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@SpecialService
public class SpecialGreetingService implements GreetingService {

    @Override
    public String greet(String name) {
        return "This is a special greeting for " + name;
    }

}
