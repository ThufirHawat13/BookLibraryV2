package com.example.booklibraryv2.security.services;

import com.example.booklibraryv2.security.entitites.RefreshTokenEntity;
import com.example.booklibraryv2.security.repositories.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenEntity findByUserId(long id) {
    return refreshTokenRepository.findByUserId(id).orElse(null);
  }

  @Transactional
  public void save(RefreshTokenEntity entity) {
    refreshTokenRepository.save(entity);
  }
}
