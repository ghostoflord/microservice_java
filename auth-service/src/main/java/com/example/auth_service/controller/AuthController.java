package com.example.auth_service.controller;

import com.example.auth_service.domain.UserAuth;
import com.example.auth_service.domain.request.LoginRequest;
import com.example.auth_service.jwt.JwtService;
import com.example.auth_service.repository.UserAuthRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserAuthRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody UserAuth req) {
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        req.setCreatedAt(Instant.now());
        req.setUpdatedAt(Instant.now());
        userRepo.save(req);
        return "Registered";
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UserAuth user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken);
    }

    @PostMapping("/refresh-token")
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        // TODO: Validate refresh token, check revoked, etc.
        // Decode refresh token, get userId, load user, generate new access token
        // Để đơn giản mình chưa implement, bạn mở rộng sau nhé

        throw new UnsupportedOperationException("Chưa implement refresh token logic");
    }
}
