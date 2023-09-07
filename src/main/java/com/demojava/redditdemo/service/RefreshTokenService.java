package com.demojava.redditdemo.service;

import com.demojava.redditdemo.dto.RefreshTokenRequest;
import com.demojava.redditdemo.exception.SpringRedditException;
import com.demojava.redditdemo.model.RefreshToken;
import com.demojava.redditdemo.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }
    public void validateRefreshToken(String refreshToken){
        System.out.println(refreshToken);
        refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new SpringRedditException("Token invalido"));

    }
    public void  deleteRefreshToken(String refreshToken){
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
