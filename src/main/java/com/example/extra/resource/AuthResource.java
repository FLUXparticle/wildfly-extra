/*
 * JBoss, Home of Professional Open Source
 * Copyright 2018, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.extra.resource;

import com.example.extra.auth.*;
import com.example.extra.user.*;
import com.nimbusds.jwt.*;
import jakarta.inject.*;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.security.*;
import java.text.*;
import java.util.logging.*;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger log = Logger.getLogger(AuthResource.class.getName());

    @Inject
    private JwtManager jwtManager;

    @Inject
    private UserService service;

    @Inject
    private SecurityContext securityContext;

    //Security constraints are defined in web.xml

    @GET
    @Path("/customer")
    public String getCustomerJSON() {
        return "{\"path\":\"customer\",\"result\":" + sayHello() + "}";
    }

    @GET
    @Path("/protected")
    public String getProtectedJSON() {
        return "{\"path\":\"protected\",\"result\":" + sayHello() + "}";
    }

    @GET
    @Path("/public")
    public String getPublicJSON() {
        return "{\"path\":\"public\",\"result\":" + sayHello() + "}";
    }

    @GET
    @Path("/claims")
    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                JWT j = JWTParser.parse(auth.substring(7));
                return Response.ok(j.getJWTClaimsSet().getClaims())
                        .build(); //Note: nimbusds converts token expiration time to milliseconds
            } catch (ParseException e) {
                log.warning(e.toString());
                return Response.status(400).build();
            }
        }
        return Response.noContent().build(); //no jwt means no claims to extract
    }

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postJWT(@FormParam("username") String username, @FormParam("password") String password) {
        log.info("Authenticating " + username);
        try {
            User user = service.authenticate(username, password);
            if (user != null) {
                if (user.getName() != null) {
                    log.info("Generating JWT for org.jboss.user " + user.getName());
                }
                String token = jwtManager.createJwt(user.getName(), user.getRoles());
                return Response.ok(
                        Json.createObjectBuilder()
                                .add("token", token)
                                .build()
                ).build();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private String sayHello() {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String principalName = userPrincipal == null ? "anonymous" : userPrincipal.getName();
        return "\"Hello " + principalName + "!\"";
    }
}
