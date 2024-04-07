package com.example.booklibraryv2.security.controllers;

import com.example.booklibraryv2.security.jwt.JwtIssuer;
import com.example.booklibraryv2.security.models.LoginRequest;
import com.example.booklibraryv2.security.models.LoginResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private final JwtIssuer jwtIssuer;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
    String token = jwtIssuer.issue(1, loginRequest.getUsername(), List.of("USER"));
    return LoginResponse.builder()
        .accessToken(token)
        .build();
  }
}
