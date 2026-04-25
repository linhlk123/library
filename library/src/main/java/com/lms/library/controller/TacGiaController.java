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
import com.lms.library.dto.request.TacGiaCreationRequest;
import com.lms.library.dto.request.TacGiaUpdateRequest;
import com.lms.library.dto.response.TacGiaResponse;
import com.lms.library.service.TacGiaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tacgia")
@RequiredArgsConstructor
public class TacGiaController {

    private final TacGiaService tacGiaService;

    @PostMapping
    public ApiResponse<TacGiaResponse> createTacGia(@RequestBody TacGiaCreationRequest request) {
        return ApiResponse.<TacGiaResponse>builder()
                .code(1000)
                .message("Tạo tác giả thành công")
                .result(tacGiaService.createTacGia(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<TacGiaResponse>> getAllTacGia() {
        return ApiResponse.<List<TacGiaResponse>>builder()
                .code(1000)
                .message("Lấy danh sách tác giả thành công")
                .result(tacGiaService.getAllTacGia())
                .build();
    }

    @GetMapping("/{maTacGia}")
    public ApiResponse<TacGiaResponse> getTacGiaById(@PathVariable Integer maTacGia) {
        return ApiResponse.<TacGiaResponse>builder()
                .code(1000)
                .message("Lấy thông tin tác giả thành công")
                .result(tacGiaService.getTacGiaById(maTacGia))
                .build();
    }

    @PutMapping("/{maTacGia}")
    public ApiResponse<TacGiaResponse> updateTacGia(
            @PathVariable Integer maTacGia,
            @RequestBody TacGiaUpdateRequest request) {
        return ApiResponse.<TacGiaResponse>builder()
                .code(1000)
                .message("Cập nhật tác giả thành công")
                .result(tacGiaService.updateTacGia(maTacGia, request))
                .build();
    }

    @DeleteMapping("/{maTacGia}")
    public ApiResponse<String> deleteTacGia(@PathVariable Integer maTacGia) {
        tacGiaService.deleteTacGia(maTacGia);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa tác giả thành công")
                .result("Xóa tác giả thành công")
                .build();
    }
}