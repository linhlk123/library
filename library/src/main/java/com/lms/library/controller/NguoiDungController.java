package com.lms.library.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
import com.lms.library.dto.request.NguoiDungCreationRequest;
import com.lms.library.dto.request.NguoiDungUpdateRequest;
import com.lms.library.dto.response.NguoiDungResponse;
import com.lms.library.service.NguoiDungService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NguoiDungController {
    private NguoiDungService nguoiDungService;

    // Endpoint to create a new user
    @PostMapping
    ApiResponse<NguoiDungResponse> createUser(@RequestBody @Valid NguoiDungCreationRequest request) {
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.createUser(request))
                .build();
    }

    // Endpoint to get all users
    @GetMapping
    ApiResponse<List<NguoiDungResponse>> getAllUsers() {
        // Log authenticated user info
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Authenticated user: {}", authentication.getName());
        authentication.getAuthorities().forEach(authority -> log.info("Authority: {}", authority.getAuthority()));

        return ApiResponse.<List<NguoiDungResponse>>builder()
                .result(nguoiDungService.getAllUsers())
                .build();
    }

    // Endpoint to get user by ID
    @GetMapping("/{id}")
    ApiResponse<NguoiDungResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.getUserById(id))
                .build();
    }

    // Endpoint to update user
    @PutMapping("/{id}")
    ApiResponse<NguoiDungResponse> updateUser(@PathVariable("id") String id,
            @RequestBody NguoiDungUpdateRequest request) {
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.updateUser(id, request))
                .build();
    }

    // Endpoint to delete user by ID
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id) {
        nguoiDungService.deleteUser(id);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    // Endpoint to get current user profile
    @GetMapping("/me")
    ApiResponse<NguoiDungResponse> getUser() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.getUserById(userId))
                .build();
    }

    // Endpoint to update current user profile
    @PutMapping("/me")
    ApiResponse<NguoiDungResponse> updateCurrentUser(@RequestBody NguoiDungUpdateRequest request) {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.updateUser(userId, request))
                .build();
    }

}