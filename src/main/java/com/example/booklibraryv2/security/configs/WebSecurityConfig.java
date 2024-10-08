package com.example.booklibraryv2.security.configs;

import com.example.booklibraryv2.security.entitites.Role;
import com.example.booklibraryv2.security.jwt.JwtAuthenticationFilter;
import com.example.booklibraryv2.security.services.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomUserDetailService userDetailsService;
  private final String BOOKS_ENDPOINT = "/books";
  private final String LIBRARY_USER_ENDPOINT = "/libraryUsers";

  @Bean
  public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    http.formLogin(AbstractHttpConfigurer::disable);

    http.sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(request ->
        request
            .requestMatchers("/error", "/auth/login")
            .permitAll()
            .requestMatchers("/auth/refresh-tokens")
            .authenticated()
            .requestMatchers(HttpMethod.POST, BOOKS_ENDPOINT + "/add")
            .hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.PATCH, BOOKS_ENDPOINT + "/update")
            .hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.DELETE, BOOKS_ENDPOINT + "/delete")
            .hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.POST, LIBRARY_USER_ENDPOINT + "/add")
            .hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.PATCH, LIBRARY_USER_ENDPOINT + "/update")
            .hasRole(Role.ADMIN.name())
            .requestMatchers(HttpMethod.DELETE, LIBRARY_USER_ENDPOINT + "/delete")
            .hasRole(Role.ADMIN.name())
            .anyRequest().hasAnyRole(Role.ADMIN.name(), Role.USER.name()));

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

    builder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());

    return builder.build();
  }
}
