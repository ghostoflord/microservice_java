package com.example.auth_service.service;

import com.example.auth_service.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role role);

    Optional<Role> findById(Long id);

    List<Role> findAll();

    Optional<Role> findByName(String name);

    void deleteById(Long id);
}
