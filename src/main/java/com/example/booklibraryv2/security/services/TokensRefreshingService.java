package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.jwt.JwtIssuer;
import com.example.booklibraryv2.security.models.LoginResponse;
import com.example.booklibraryv2.security.models.userPrincipal.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokensRefreshingService {

  private final JwtIssuer jwtIssuer;

  public LoginResponse refreshTokens() {
    UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    return LoginResponse.builder()
        .accessToken(jwtIssuer.issueJwt(userPrincipal.getId(), userPrincipal.getUsername(),
            userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())))
        .refreshToken(
            jwtIssuer.issueRefreshToken(userPrincipal.getId(), userPrincipal.getUsername()))
        .build();
  }
}
