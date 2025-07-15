package com.example.auth_service.controller;

import com.example.auth_service.domain.UserAuth;
import com.example.auth_service.service.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/user-auth")
    public List<UserAuth> getAll() {
        return userAuthService.findAll();
    }

    @GetMapping("/user-auth/{id}")
    public ResponseEntity<UserAuth> getById(@PathVariable Long id) {
        return userAuthService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user-auth")
    public UserAuth create(@RequestBody UserAuth userAuth) {
        return userAuthService.save(userAuth);
    }

    @PutMapping("/user-auth/{id}")
    public ResponseEntity<UserAuth> update(@PathVariable Long id, @RequestBody UserAuth updatedUser) {
        return userAuthService.findById(id)
                .map(existing -> {
                    existing.setEmail(updatedUser.getEmail());
                    existing.setPhone(updatedUser.getPhone());
                    existing.setPassword(updatedUser.getPassword());
                    existing.setUpdatedAt(updatedUser.getUpdatedAt());
                    return ResponseEntity.ok(userAuthService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user-auth/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userAuthService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
