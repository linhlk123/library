package com.lms.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.VaiTroRequest;
import com.lms.library.dto.response.VaiTroResponse;
import com.lms.library.service.VaiTroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VaiTroController {
    VaiTroService roleService;

    @PostMapping
    ApiResponse<VaiTroResponse> createRole(@RequestBody @Valid VaiTroRequest request) {
        return ApiResponse.<VaiTroResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<VaiTroResponse>> getAllRoles() {
        return ApiResponse.<List<VaiTroResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> deleteRole(@PathVariable String name) {
        roleService.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
