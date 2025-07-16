package com.example.auth_service.config;

import com.example.auth_service.domain.UserAuth;
import com.example.auth_service.repository.UserAuthRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userRepo;

    public CustomUserDetailsService(UserAuthRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserAuth user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(String.valueOf(user.getId()))
                .password(user.getPassword()) // không dùng khi xác thực từ JWT
                .authorities(user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList()))
                .build();
    }

}
