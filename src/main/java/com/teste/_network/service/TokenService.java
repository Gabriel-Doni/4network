package com.teste._network.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teste._network.entity.Client;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Client client) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create().withIssuer("4network-api")
                    .withSubject(client.getName())
                    .withClaim("idCliente", client.getIdClient().toString())
                    .withExpiresAt(generetaExpirationDate())
                    .sign(algorithm)
                    .toString();

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token ", e);
        }
    }

    public String validateToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("4network-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public UUID getIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("4network-api")
                    .build()
                    .verify(token);

            return UUID.fromString(decodedJWT.getClaim("idCliente").asString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token inválido ou não autorizado", e);
        }
    }

    private Instant generetaExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
