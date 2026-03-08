package com.lms.library.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.UserCreationRequest;
import com.lms.library.dto.request.UserUpdateRequest;
import com.lms.library.dto.response.UserResponse;
import com.lms.library.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    private UserService userService;

    // Endpoint để tạo người dùng mới
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }
    
    // Endpoint để lấy danh sách tất cả người dùng
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        // Log thong tin người dùng đã xác thực
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        
        log.info("Authenticated user: {}", authentication.getName());
        authentication.getAuthorities().forEach(authority -> 
            log.info("Authority: {}", authority.getAuthority())
        );

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }



    // Endpoint để lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }

    // Endpoint để cập nhật thông tin người dùng
    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    // Endpoint để xóa người dùng theo ID
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @GetMapping("/me")
    ApiResponse<UserResponse> getUser() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }
    
}