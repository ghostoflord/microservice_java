package com.example.auth_service.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.auth_service.domain.UserAuth;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${ghost.jwt.access-token-validity-in-seconds}")
    private long accessTokenValidity;

    @Value("${ghost.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidity;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateAccessToken(UserAuth user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(accessTokenValidity, ChronoUnit.MILLIS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(expiry)
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream().map(r -> r.getName()).toList())
                .build();

        // ⚠️ BẮT BUỘC: Phải có JwsHeader định nghĩa thuật toán
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS512).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public String generateRefreshToken(UserAuth user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(refreshTokenValidity, ChronoUnit.MILLIS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(expiry)
                .claim("typ", "refresh")
                .id(UUID.randomUUID().toString())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS512).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
