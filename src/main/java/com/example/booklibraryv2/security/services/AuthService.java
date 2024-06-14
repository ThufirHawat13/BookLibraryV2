package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.entitites.RefreshTokenEntity;
import com.example.booklibraryv2.security.entitites.UserEntity;
import com.example.booklibraryv2.security.jwt.JwtIssuer;
import com.example.booklibraryv2.security.models.LoginResponse;
import com.example.booklibraryv2.security.models.userPrincipal.UserPrincipal;
import com.example.booklibraryv2.security.repositories.RefreshTokenRepository;
import com.example.booklibraryv2.security.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtIssuer jwtIssuer;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  @Transactional
  public LoginResponse attemptLogin(String username, String password) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    UserEntity user = userRepository.findById(principal.getId())
        .orElseThrow(() -> new RuntimeException("User with id %d isn't found!"
            .formatted(principal.getId())));

    String accessToken = jwtIssuer.issueJwt(principal.getId(), principal.getUsername(),
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));

    String refreshToken = jwtIssuer.issueRefreshToken(user.getId(), user.getUsername());

    refreshTokenRepository.save(RefreshTokenEntity.builder()
        .id(user.getId())
        .user(user)
        .token(refreshToken)
        .build());

    return LoginResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Transactional
  public LoginResponse tryToRefreshTokens(HttpServletRequest httpServletRequest) {
    UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    UserEntity user = userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new RuntimeException("User with id = %d isn't found!"
            .formatted(userPrincipal.getId())));

    RefreshTokenEntity refreshTokenEntity;
    String refreshToken = jwtIssuer.issueRefreshToken(userPrincipal.getId(),
        userPrincipal.getUsername());

    //TODO сделать двунаправленную связь с энтитей юзер и юзать просто файнд бай айди у юзера,
    // потом уже получать токен с юзера + добавить чек рефреш токена в цепочке

    if (((refreshTokenEntity = user.getRefreshToken()) != null)
        && extractTokenFromHeader(httpServletRequest).equals(refreshTokenEntity.getToken())) {
      refreshTokenEntity.setToken(refreshToken);
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

  private String extractTokenFromHeader(HttpServletRequest httpServletRequest) {
   return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
  }

}
