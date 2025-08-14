package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.BlacklistedToken;
import org.example.repository.TokenBlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenBlacklistRepository blacklistRepository;
    private final JwtService jwtService;

    public void logout(String token) {
        String cleanToken = token.replace("Bearer ", "").trim();
        Date expiration = jwtService.extractExpiration(cleanToken);

        blacklistRepository.save(
                BlacklistedToken.builder()
                        .token(cleanToken)
                        .expiration(expiration)
                        .build()
        );
    }

    public boolean isBlacklisted(String token) {
        return blacklistRepository.existsByToken(token);
    }
}

