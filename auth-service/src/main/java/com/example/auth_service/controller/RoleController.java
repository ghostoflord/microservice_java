package com.example.auth_service.controller;

import com.example.auth_service.domain.Role;
import com.example.auth_service.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("roles")
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("roles")
    public Role create(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role updatedRole) {
        return roleService.findById(id)
                .map(existing -> {
                    existing.setName(updatedRole.getName());
                    existing.setDescription(updatedRole.getDescription());
                    existing.setUpdatedAt(updatedRole.getUpdatedAt());
                    return ResponseEntity.ok(roleService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
