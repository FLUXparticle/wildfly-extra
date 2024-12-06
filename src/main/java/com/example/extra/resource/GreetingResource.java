package com.example.extra.resource;

import com.example.extra.service.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

import static java.util.Collections.*;

@Path("/greeting")
public class GreetingResource {

    @Inject
    // @SpecialService
    private GreetingService greetingService;

    @POST
    @Path("/hello")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> hello(HelloRequest request) {
        String name = request.getName();
        String greeting = greetingService.greet(name);
        return singletonMap("greeting", greeting);
    }

    public static class HelloRequest {
        public String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}
