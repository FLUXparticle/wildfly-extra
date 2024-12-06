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
package com.example.extra.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import jakarta.enterprise.context.*;
import jakarta.json.*;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;

@ApplicationScoped
public class JwtManager {

    static {
        final String configDir = System.getProperty("jboss.server.config.dir");
        final Path keyStore = Path.of(configDir, "jwt.keystore");
        char[] password = "secret".toCharArray();
        String alias = "jwt-auth";
        PrivateKey pk = null;
        try (InputStream in = Files.newInputStream(keyStore)) {
            final KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(in, password);
            Key key = ks.getKey(alias, password);
            if (key instanceof PrivateKey) {
                pk = (PrivateKey) key;
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        privateKey = pk;
    }

    private static final PrivateKey privateKey;
    private static final int TOKEN_VALIDITY = 14400;
    private static final String ISSUER = "quickstart-jwt-issuer";
    private static final String AUDIENCE = "jwt-audience";

    public String createJwt(final String subject, final String[] roles) throws Exception {
        final JWSSigner signer = new RSASSASigner(privateKey);
        final JsonArrayBuilder rolesBuilder = Json.createArrayBuilder(List.of(roles));
        final JsonObjectBuilder claimsBuilder = Json.createObjectBuilder()
                .add("sub", subject)
                .add("iss", ISSUER)
                .add("aud", AUDIENCE)
                .add("groups", rolesBuilder.build())
                .add("exp", ((System.currentTimeMillis() / 1000) + TOKEN_VALIDITY));

        final JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(new JOSEObjectType("jwt")).build(),
                new Payload(claimsBuilder.build().toString()));

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }
}
