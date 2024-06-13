package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.entitites.Role;
import com.example.booklibraryv2.security.entitites.UserEntity;
import com.example.booklibraryv2.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserEntity findUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("User " + username + "isn't found!"));

    return user;
  }

  public UserEntity findById(long id) {
    return userRepository.findById(id).orElse(null);
  }
}
