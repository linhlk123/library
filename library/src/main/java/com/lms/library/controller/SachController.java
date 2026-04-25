package com.lms.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.SachCreationRequest;
import com.lms.library.dto.request.SachUpdateRequest;
import com.lms.library.dto.response.PageResponseDTO;
import com.lms.library.dto.response.SachResponse;
import com.lms.library.service.SachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sach")
@RequiredArgsConstructor
public class SachController {

    private final SachService sachService;

    @PostMapping
    public ApiResponse<SachResponse> createSach(@RequestBody SachCreationRequest request) {
        return ApiResponse.<SachResponse>builder()
                .code(1000)
                .message("Tạo sách thành công")
                .result(sachService.createSach(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponseDTO<SachResponse>> getAllSach(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponseDTO<SachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách sách thành công")
                .result(sachService.getAllSach(page, size))
                .build();
    }

    @GetMapping("/{maSach}")
    public ApiResponse<SachResponse> getSachById(@PathVariable Integer maSach) {
        return ApiResponse.<SachResponse>builder()
                .code(1000)
                .message("Lấy thông tin sách thành công")
                .result(sachService.getSachById(maSach))
                .build();
    }

    @GetMapping("/dausach/{maDauSach}")
    public ApiResponse<List<SachResponse>> getSachByDauSach(@PathVariable Integer maDauSach) {
        return ApiResponse.<List<SachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách sách theo đầu sách thành công")
                .result(sachService.getSachByDauSach(maDauSach))
                .build();
    }

    @PutMapping("/{maSach}")
    public ApiResponse<SachResponse> updateSach(
            @PathVariable Integer maSach,
            @RequestBody SachUpdateRequest request) {
        return ApiResponse.<SachResponse>builder()
                .code(1000)
                .message("Cập nhật sách thành công")
                .result(sachService.updateSach(maSach, request))
                .build();
    }

    @DeleteMapping("/{maSach}")
    public ApiResponse<String> deleteSach(@PathVariable Integer maSach) {
        sachService.deleteSach(maSach);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa sách thành công")
                .result("Xóa sách thành công")
                .build();
    }
}