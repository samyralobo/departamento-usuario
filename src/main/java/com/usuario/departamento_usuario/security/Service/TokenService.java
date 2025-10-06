package com.usuario.departamento_usuario.security.Service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.usuario.departamento_usuario.models.User;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService{
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("departamento-usuario")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpiration())
                    .sign(algorithm);

        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token:", exception);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("departamento-usuario")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant generateExpiration() {
        return LocalDateTime.now()
                .plusHours(4)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
