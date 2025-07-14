// UserProfileService.java
package com.vn.user_service.service;

import com.vn.user_service.domain.UserProfile;
import com.vn.user_service.dto.UserProfileDTO;
import com.vn.user_service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public List<UserProfile> findAll() {
        return repository.findAll();
    }

    public Optional<UserProfile> findById(Long id) {
        return repository.findById(id);
    }

    public UserProfile create(UserProfileDTO dto) {
        UserProfile user = new UserProfile();
        user.setFullName(dto.fullName);
        user.setAvatarUrl(dto.avatarUrl);
        user.setGender(dto.gender);
        user.setDob(dto.dob);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return repository.save(user);
    }

    public UserProfile update(Long id, UserProfileDTO dto) {
        UserProfile user = repository.findById(id).orElseThrow();
        user.setFullName(dto.fullName);
        user.setAvatarUrl(dto.avatarUrl);
        user.setGender(dto.gender);
        user.setDob(dto.dob);
        user.setUpdatedAt(Instant.now());
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
