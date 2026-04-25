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
import com.lms.library.dto.request.BaoCaoTinhHinhMuonSachCreationRequest;
import com.lms.library.dto.request.BaoCaoTinhHinhMuonSachUpdateRequest;
import com.lms.library.dto.response.BaoCaoTinhHinhMuonSachResponse;
import com.lms.library.service.BaoCaoTinhHinhMuonSachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bctinhhinhmuonsach")
@RequiredArgsConstructor
public class BaoCaoTinhHinhMuonSachController {

    private final BaoCaoTinhHinhMuonSachService service;

    @PostMapping
    public ApiResponse<BaoCaoTinhHinhMuonSachResponse> create(
            @RequestBody BaoCaoTinhHinhMuonSachCreationRequest request) {
        return ApiResponse.<BaoCaoTinhHinhMuonSachResponse>builder()
                .code(1000)
                .message("Tạo báo cáo tình hình mượn sách thành công")
                .result(service.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<BaoCaoTinhHinhMuonSachResponse>> getAll() {
        return ApiResponse.<List<BaoCaoTinhHinhMuonSachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách báo cáo tình hình mượn sách thành công")
                .result(service.getAll())
                .build();
    }

    @GetMapping("/{maBCTHMS}")
    public ApiResponse<BaoCaoTinhHinhMuonSachResponse> getById(@PathVariable Integer maBCTHMS) {
        return ApiResponse.<BaoCaoTinhHinhMuonSachResponse>builder()
                .code(1000)
                .message("Lấy thông tin báo cáo tình hình mượn sách thành công")
                .result(service.getById(maBCTHMS))
                .build();
    }

    @PutMapping("/{maBCTHMS}")
    public ApiResponse<BaoCaoTinhHinhMuonSachResponse> update(
            @PathVariable Integer maBCTHMS,
            @RequestBody BaoCaoTinhHinhMuonSachUpdateRequest request) {
        return ApiResponse.<BaoCaoTinhHinhMuonSachResponse>builder()
                .code(1000)
                .message("Cập nhật báo cáo tình hình mượn sách thành công")
                .result(service.update(maBCTHMS, request))
                .build();
    }

    @DeleteMapping("/{maBCTHMS}")
    public ApiResponse<String> delete(@PathVariable Integer maBCTHMS) {
        service.delete(maBCTHMS);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa báo cáo tình hình mượn sách thành công")
                .result("Xóa báo cáo tình hình mượn sách thành công")
                .build();
    }
}