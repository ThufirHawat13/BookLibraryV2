package com.example.booklibraryv2.security.controllers;

import com.example.booklibraryv2.security.models.LoginRequest;
import com.example.booklibraryv2.security.models.LoginResponse;
import com.example.booklibraryv2.security.models.RefreshToken;
import com.example.booklibraryv2.security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
//  private final TokensRefreshingService tokensRefreshingService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
    return authService.attemptLogin(loginRequest.getUsername(), loginRequest.getPassword());
  }

//  @PostMapping("/refresh-tokens")
//  public LoginResponse refreshTokens(@RequestBody @Validated RefreshToken refreshToken) {
//    return tokensRefreshingService.refreshTokens(refreshToken);
//  }
}
