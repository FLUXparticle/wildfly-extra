package com.example.extra.service;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

@Decorator
public class GreetingDecorator implements GreetingService {

    @Inject
    @Delegate
    private GreetingService delegate;

    @Override
    public String greet(String name) {
        // Vorherige Logik ausführen
        String originalGreeting = delegate.greet(name);
        // Zusätzliche Logik hinzufügen
        return originalGreeting + ". Have a great day!";
    }

}
