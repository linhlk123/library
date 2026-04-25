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
import com.lms.library.dto.request.CuonSachCreationRequest;
import com.lms.library.dto.request.CuonSachUpdateRequest;
import com.lms.library.dto.response.CuonSachResponse;
import com.lms.library.service.CuonSachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cuonsach")
@RequiredArgsConstructor
public class CuonSachController {

    private final CuonSachService cuonSachService;

    @PostMapping
    public ApiResponse<CuonSachResponse> createCuonSach(@RequestBody CuonSachCreationRequest request) {
        return ApiResponse.<CuonSachResponse>builder()
                .code(1000)
                .message("Tạo cuốn sách thành công")
                .result(cuonSachService.createCuonSach(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CuonSachResponse>> getAllCuonSach() {
        return ApiResponse.<List<CuonSachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách cuốn sách thành công")
                .result(cuonSachService.getAllCuonSach())
                .build();
    }

    @GetMapping("/{maCuonSach}")
    public ApiResponse<CuonSachResponse> getCuonSachById(@PathVariable Integer maCuonSach) {
        return ApiResponse.<CuonSachResponse>builder()
                .code(1000)
                .message("Lấy thông tin cuốn sách thành công")
                .result(cuonSachService.getCuonSachById(maCuonSach))
                .build();
    }

    @GetMapping("/sach/{maSach}")
    public ApiResponse<List<CuonSachResponse>> getCuonSachBySach(@PathVariable Integer maSach) {
        return ApiResponse.<List<CuonSachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách cuốn sách theo sách thành công")
                .result(cuonSachService.getCuonSachBySach(maSach))
                .build();
    }

    @PutMapping("/{maCuonSach}")
    public ApiResponse<CuonSachResponse> updateCuonSach(
            @PathVariable Integer maCuonSach,
            @RequestBody CuonSachUpdateRequest request) {
        return ApiResponse.<CuonSachResponse>builder()
                .code(1000)
                .message("Cập nhật cuốn sách thành công")
                .result(cuonSachService.updateCuonSach(maCuonSach, request))
                .build();
    }

    @DeleteMapping("/{maCuonSach}")
    public ApiResponse<String> deleteCuonSach(@PathVariable Integer maCuonSach) {
        cuonSachService.deleteCuonSach(maCuonSach);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa cuốn sách thành công")
                .result("Xóa cuốn sách thành công")
                .build();
    }
}