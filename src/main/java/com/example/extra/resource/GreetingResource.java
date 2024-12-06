package com.example.extra.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

import static java.util.Collections.*;

@Path("/greeting")
public class GreetingResource {

    @POST
    @Path("/hello")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> hello(HelloRequest request) {
        String name = request.getName();
        return singletonMap("greeting", "Hello " + name);
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
