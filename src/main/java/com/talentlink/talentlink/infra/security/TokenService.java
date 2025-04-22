package com.talentlink.talentlink.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.talentlink.talentlink.domain.company.Company;
import com.talentlink.talentlink.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public Collection<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return Arrays.stream(JWT.require(algorithm)
                            .withIssuer("USER", "COMPANY")
                            .build()
                            .verify(token)
                            .getClaim("authorities")
                            .asArray(String.class))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (JWTVerificationException exception) {
            return null;
        }
    }


    public String generateToken(User user) {
        return generateToken(user.getEmail(), "USER");
    }

    public String generateToken(Company company) {
        return generateToken(company.getEmail(), "COMPANY");
    }

    private String generateToken(String subject, String issuer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(subject)
                    .withClaim("authorities", Collections.singletonList("ROLE_" + issuer))
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("USER", "COMPANY")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
