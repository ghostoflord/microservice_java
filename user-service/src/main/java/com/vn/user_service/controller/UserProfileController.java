// UserProfileController.java
package com.vn.user_service.controller;

import com.vn.user_service.domain.UserProfile;
import com.vn.user_service.dto.UserProfileDTO;
import com.vn.user_service.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping("/user-profiles")
    public List<UserProfile> getAll() {
        return service.findAll();
    }

    @GetMapping("/user-profiles/{id}")
    public UserProfile getById(@PathVariable Long id) {
        return service.findById(id).orElseThrow();
    }

    @PostMapping("/user-profiles")
    public UserProfile create(@RequestBody UserProfileDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/user-profiles/{id}")
    public UserProfile update(@PathVariable Long id, @RequestBody UserProfileDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/user-profiles/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
