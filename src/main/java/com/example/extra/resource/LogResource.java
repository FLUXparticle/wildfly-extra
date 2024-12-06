package com.example.extra.resource;

import com.example.extra.service.*;
import jakarta.ejb.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/log")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LogResource {

    @Inject
    private LogService logService;

    @GET
    @Path("/test")
    public Map<String, Object> test() {
        return logService.test();
    }

    private void test1(String str) {
        // empty
    }

    private void test2(String str1, String str2) {
        // empty
    }

}
