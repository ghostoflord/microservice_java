package com.vn.user_service.controller;

import com.vn.user_service.domain.UserProfile;
import com.vn.user_service.domain.response.RestResponse;
import com.vn.user_service.dto.UserProfileDTO;
import com.vn.user_service.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RestResponse<List<UserProfile>>> getAll() {
        List<UserProfile> userList = service.findAll();
        RestResponse<List<UserProfile>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(userList);
        response.setMessage("Lấy danh sách người dùng thành công");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-profiles/{id}")
    public ResponseEntity<RestResponse<UserProfile>> getById(@PathVariable Long id) {
        UserProfile user = service.findById(id).orElseThrow(() -> new RuntimeException("User không tồn tại"));

        RestResponse<UserProfile> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(user);
        response.setMessage("Lấy thông tin người dùng thành công");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/user-profiles")
    public ResponseEntity<RestResponse<UserProfile>> create(@RequestBody UserProfileDTO dto) {
        UserProfile created = service.create(dto);

        RestResponse<UserProfile> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(created);
        response.setMessage("Tạo người dùng thành công");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/user-profiles/{id}")
    public ResponseEntity<RestResponse<UserProfile>> update(@PathVariable Long id, @RequestBody UserProfileDTO dto) {
        UserProfile updated = service.update(id, dto);

        RestResponse<UserProfile> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(updated);
        response.setMessage("Cập nhật người dùng thành công");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user-profiles/{id}")
    public ResponseEntity<RestResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Xóa người dùng thành công");

        return ResponseEntity.ok(response);
    }
}
