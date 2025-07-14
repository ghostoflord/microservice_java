package com.vn.user_service.controller;

import com.vn.user_service.domain.UserAddress;
import com.vn.user_service.dto.UserAddressDTO;
import com.vn.user_service.service.UserAddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserAddressController {

    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @GetMapping("/user-addresses")
    public List<UserAddress> getAll() {
        return userAddressService.findAll();
    }

    @GetMapping("user-addresses/user/{userId}")
    public List<UserAddress> getByUserId(@PathVariable Long userId) {
        return userAddressService.findByUserId(userId);
    }

    @PostMapping("/user-addresses")
    public UserAddress create(@RequestBody UserAddressDTO dto) {
        return userAddressService.create(dto);
    }

    @PutMapping("/user-addresses/{id}")
    public UserAddress update(@PathVariable Long id, @RequestBody UserAddressDTO dto) {
        return userAddressService.update(id, dto);
    }

    @DeleteMapping("/user-addresses/{id}")
    public void delete(@PathVariable Long id) {
        userAddressService.delete(id);
    }
}
