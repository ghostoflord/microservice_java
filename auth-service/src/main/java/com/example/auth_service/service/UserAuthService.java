package com.example.auth_service.service;

import com.example.auth_service.domain.UserAuth;
import java.util.List;
import java.util.Optional;

public interface UserAuthService {
    UserAuth save(UserAuth user);

    Optional<UserAuth> findById(Long id);

    List<UserAuth> findAll();

    void deleteById(Long id);
}
