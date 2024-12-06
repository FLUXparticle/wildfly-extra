package com.example.extra.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class AlternativeGreetingService implements GreetingService {

    @Override
    public String greet(String name) {
        return "This is an alternative greeting for " + name;
    }

}
