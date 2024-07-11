package com.mthor.blogchallenge.infra.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mthor.blogchallenge.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTProvider {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("mthor")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(getExpDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Instant getExpDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-07:00"));
    }

    public String getSubject(String token){
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm= Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("mthor")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Fail verification.");
        }
        if (verifier.getSubject() == null){
            throw new RuntimeException("Invalid verifier.");
        }
        return verifier.getSubject();
    }
}
