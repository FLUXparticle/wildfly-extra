package com.example.extra.service;

import com.example.extra.interceptor.*;
import jakarta.enterprise.context.*;
import jakarta.interceptor.*;
import org.jboss.logging.*;

import java.util.*;

import static java.util.Collections.*;

@ApplicationScoped
@Interceptors(LoggingInterceptor.class)
public class LogService {

    private static final Logger LOGGER = Logger.getLogger(LogService.class);

    public Map<String, Object> test() {
        test1("test1");
        test2("test1", "test2");
        return emptyMap();
    }

    private void test1(String str) {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            LOGGER.error("runtime exception", e);
        }
    }

    private void test2(String str1, String str2) {
        // empty
    }

}
