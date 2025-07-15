package com.example.auth_service.service.implementation;

import com.example.auth_service.domain.UserAuth;
import com.example.auth_service.repository.UserAuthRepository;
import com.example.auth_service.service.UserAuthService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthRepository userAuthRepository;

    public UserAuthServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserAuth save(UserAuth user) {
        return userAuthRepository.save(user);
    }

    @Override
    public Optional<UserAuth> findById(Long id) {
        return userAuthRepository.findById(id);
    }

    @Override
    public List<UserAuth> findAll() {
        return userAuthRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userAuthRepository.deleteById(id);
    }
}
