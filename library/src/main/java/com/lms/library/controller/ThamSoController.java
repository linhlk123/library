package com.lms.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.ThamSoCreationRequest;
import com.lms.library.dto.request.ThamSoUpdateRequest;
import com.lms.library.dto.response.ThamSoResponse;
import com.lms.library.service.ThamSoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/thamso")
@RequiredArgsConstructor
public class ThamSoController {

    private final ThamSoService thamSoService;

    @PostMapping
    public ApiResponse<ThamSoResponse> createThamSo(@RequestBody ThamSoCreationRequest request) {
        return ApiResponse.<ThamSoResponse>builder()
                .code(1000)
                .message("Tạo tham số thành công")
                .result(thamSoService.createThamSo(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ThamSoResponse>> getAllThamSo() {
        return ApiResponse.<List<ThamSoResponse>>builder()
                .code(1000)
                .message("Lấy danh sách tham số thành công")
                .result(thamSoService.getAllThamSo())
                .build();
    }

    @GetMapping("/{tenThamSo}")
    public ApiResponse<ThamSoResponse> getThamSoById(@PathVariable String tenThamSo) {
        return ApiResponse.<ThamSoResponse>builder()
                .code(1000)
                .message("Lấy thông tin tham số thành công")
                .result(thamSoService.getThamSoById(tenThamSo))
                .build();
    }

    @PutMapping("/{tenThamSo}")
    public ApiResponse<ThamSoResponse> updateThamSo(
            @PathVariable String tenThamSo,
            @RequestBody ThamSoUpdateRequest request) {
        return ApiResponse.<ThamSoResponse>builder()
                .code(1000)
                .message("Cập nhật tham số thành công")
                .result(thamSoService.updateThamSo(tenThamSo, request))
                .build();
    }

    @DeleteMapping("/{tenThamSo}")
    public ApiResponse<String> deleteThamSo(@PathVariable String tenThamSo) {
        thamSoService.deleteThamSo(tenThamSo);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa tham số thành công")
                .result("Xóa tham số thành công")
                .build();
    }
}