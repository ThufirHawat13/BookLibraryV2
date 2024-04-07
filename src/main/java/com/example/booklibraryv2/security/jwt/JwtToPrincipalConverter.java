package com.example.booklibraryv2.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.booklibraryv2.security.entitites.userPrincipal.UserPrincipal;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtToPrincipalConverter {

  public UserPrincipal convert(DecodedJWT decodedJwt) {
    return UserPrincipal.builder()
        .id(Long.valueOf(decodedJwt.getSubject()))
        .username(decodedJwt.getClaim("u").asString())
        .authorities(extractAuthoririesFromClaim(decodedJwt))
        .build();
  }

  private List<SimpleGrantedAuthority> extractAuthoririesFromClaim(DecodedJWT decodedJwt) {
    Claim claim = decodedJwt.getClaim("u");

    if (claim.isNull() || claim.isMissing()) {
      return List.of();
    }

    return claim.asList(SimpleGrantedAuthority.class);
  }
}
