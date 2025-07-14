package com.vn.user_service.controller;

import com.vn.user_service.domain.UserAddress;
import com.vn.user_service.domain.response.RestResponse;
import com.vn.user_service.dto.UserAddressDTO;
import com.vn.user_service.service.UserAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RestResponse<List<UserAddress>>> getAll() {
        List<UserAddress> list = userAddressService.findAll();

        RestResponse<List<UserAddress>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Lấy danh sách địa chỉ thành công");
        response.setData(list);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-addresses/user/{userId}")
    public ResponseEntity<RestResponse<List<UserAddress>>> getByUserId(@PathVariable Long userId) {
        List<UserAddress> list = userAddressService.findByUserId(userId);

        RestResponse<List<UserAddress>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Lấy địa chỉ theo người dùng thành công");
        response.setData(list);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/user-addresses")
    public ResponseEntity<RestResponse<UserAddress>> create(@RequestBody UserAddressDTO dto) {
        UserAddress created = userAddressService.create(dto);

        RestResponse<UserAddress> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Tạo địa chỉ thành công");
        response.setData(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/user-addresses/{id}")
    public ResponseEntity<RestResponse<UserAddress>> update(@PathVariable Long id, @RequestBody UserAddressDTO dto) {
        UserAddress updated = userAddressService.update(id, dto);

        RestResponse<UserAddress> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Cập nhật địa chỉ thành công");
        response.setData(updated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user-addresses/{id}")
    public ResponseEntity<RestResponse<Void>> delete(@PathVariable Long id) {
        userAddressService.delete(id);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Xóa địa chỉ thành công");

        return ResponseEntity.ok(response);
    }
}
