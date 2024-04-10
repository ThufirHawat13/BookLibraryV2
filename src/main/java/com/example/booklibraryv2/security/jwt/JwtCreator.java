package com.example.booklibraryv2.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.booklibraryv2.security.configs.JwtProperties;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtCreator {

  private final JwtProperties properties;

  public String create(Long userId, String username, List<String> roles) {
    return JWT.create()
        .withSubject(String.valueOf(userId))
        .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
        .withClaim("u", username)
        .withClaim("a", roles)
        .sign(Algorithm.HMAC256(properties.getSecretKey()));
  }
}
