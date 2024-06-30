package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.entitites.RefreshTokenEntity;
import com.example.booklibraryv2.security.jwt.JwtIssuer;
import com.example.booklibraryv2.security.models.LoginResponse;
import com.example.booklibraryv2.security.models.userPrincipal.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtIssuer jwtIssuer;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  public LoginResponse attemptLogin(String username, String password) {
    UserPrincipal principal = getUserPrincipalFromSecurityContext(authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password)));

    String refreshToken = issueRefreshToken(principal);

    refreshTokenService.save(RefreshTokenEntity.builder()
        .user(userService.findById(principal.getId()))
        .token(refreshToken)
        .build());

    return LoginResponse.builder()
        .accessToken(issueAccessToken(principal))
        .refreshToken(refreshToken)
        .build();
  }

  private UserPrincipal getUserPrincipalFromSecurityContext(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return (UserPrincipal) authentication.getPrincipal();
  }

  private String issueAccessToken(UserPrincipal principal) {
    return jwtIssuer.issueJwt(principal.getId(), principal.getUsername(),
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
  }

  private String issueRefreshToken(UserPrincipal principal) {
    return jwtIssuer.issueRefreshToken(principal.getId(), principal.getUsername());
  }

  @Transactional
  public LoginResponse tryToRefreshTokens(HttpServletRequest httpServletRequest) {
    UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    RefreshTokenEntity refreshTokenEntity;
    String refreshToken = jwtIssuer.issueRefreshToken(userPrincipal.getId(),
        userPrincipal.getUsername());

    //TODO сделать двунаправленную связь с энтитей юзер и юзать просто файнд бай айди у юзера,
    // потом уже получать токен с юзера + добавить чек рефреш токена в цепочке

    if (((refreshTokenEntity = userService.findById(userPrincipal.getId()).getRefreshToken()) != null)
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
