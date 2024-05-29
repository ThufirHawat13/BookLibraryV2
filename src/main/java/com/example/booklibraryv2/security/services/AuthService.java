package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.entitites.RefreshTokenEntity;
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
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  public LoginResponse attemptLogin(String username, String password) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

    String accessToken = jwtIssuer.issueJwt(principal.getId(), principal.getUsername(),
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));

    String refreshToken = jwtIssuer.issueRefreshToken(principal.getId(), principal.getUsername());

    refreshTokenService.save(RefreshTokenEntity.builder()
        .id(principal.getId())
        .user(userService.findById(principal.getId()))
        .token(refreshToken)
        .build());

    return LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public LoginResponse tryToRefreshTokens() {
    UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    RefreshTokenEntity refreshTokenEntity;
    String refreshToken = jwtIssuer.issueRefreshToken(userPrincipal.getId(),
        userPrincipal.getUsername());

    if ((refreshTokenEntity = refreshTokenService.findByUserId(userPrincipal.getId())) != null) {
      refreshTokenEntity.setToken(refreshToken);
      refreshTokenService.save(refreshTokenEntity);
    } else {
      return LoginResponse.builder()
          .accessToken("")
          .refreshToken("")
          .build();
    }

    return LoginResponse.builder()
        .accessToken(jwtIssuer.issueJwt(userPrincipal.getId(), userPrincipal.getUsername(),
            userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())))
        .refreshToken(refreshToken)
        .build();
  }

}
