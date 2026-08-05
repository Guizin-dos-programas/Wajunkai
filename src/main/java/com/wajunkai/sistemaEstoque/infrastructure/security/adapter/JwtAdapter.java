package com.wajunkai.sistemaEstoque.infrastructure.security.adapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wajunkai.sistemaEstoque.application.ports.outbound.TokenServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtAdapter implements TokenServicePort {

    private static final String ISSUER = "sistemaEstoque";

    @Value("${api.security.token.secret:minha-chave-secreta-super-segura-123}")
    private String secret;

    @Override
    public String gerarToken(String login) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer(ISSUER)
                    .withSubject(login)
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException jwtCreationException){
            throw new RuntimeException("Erro ao gerar token JWT de acesso", jwtCreationException);
        }
    }

    @Override
    public String validarToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException jwtVerificationException){
            System.err.println("Erro na validação do JWT: " + jwtVerificationException.getMessage());
            return null;
        }
    }

    private Instant gerarDataExpiracao(){
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }
}
