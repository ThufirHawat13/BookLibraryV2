package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.entitites.UserEntity;
import com.example.booklibraryv2.security.models.userPrincipal.UserPrincipal;
import com.example.booklibraryv2.security.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    userRepository.findAll().forEach(System.out::println);
    System.out.println(userRepository.findByUsername(username));
    UserEntity user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User " + username + "isn't found!"));

    return UserPrincipal.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
        .build();
  }
}
