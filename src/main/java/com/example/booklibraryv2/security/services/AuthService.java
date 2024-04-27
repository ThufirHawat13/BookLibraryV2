package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.jwt.JwtIssuer;
import com.example.booklibraryv2.security.models.LoginResponse;
import com.example.booklibraryv2.security.models.userPrincipal.UserPrincipal;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtIssuer jwtIssuer;

  public LoginResponse attemptLogin(String username, String password) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

    String accessToken = jwtIssuer.issueJwt(principal.getId(), principal.getUsername(),
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));

    String refreshToken = jwtIssuer.issueRefreshToken();

    return LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

}
