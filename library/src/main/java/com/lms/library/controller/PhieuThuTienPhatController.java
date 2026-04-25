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
import com.lms.library.dto.request.PhieuThuTienPhatCreationRequest;
import com.lms.library.dto.request.PhieuThuTienPhatUpdateRequest;
import com.lms.library.dto.response.PhieuThuTienPhatResponse;
import com.lms.library.service.PhieuThuTienPhatService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/phieuthutienphat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuThuTienPhatController {

    PhieuThuTienPhatService phieuThuTienPhatService;

    @PostMapping
    public ApiResponse<PhieuThuTienPhatResponse> create(@RequestBody PhieuThuTienPhatCreationRequest request) {
        return ApiResponse.<PhieuThuTienPhatResponse>builder()
                .code(1000)
                .message("Tạo phiếu thu tiền phạt thành công")
                .result(phieuThuTienPhatService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PhieuThuTienPhatResponse>> getAll() {
        return ApiResponse.<List<PhieuThuTienPhatResponse>>builder()
                .code(1000)
                .message("Lấy danh sách phiếu thu tiền phạt thành công")
                .result(phieuThuTienPhatService.getAll())
                .build();
    }

    @GetMapping("/{soPTT}")
    public ApiResponse<PhieuThuTienPhatResponse> getById(@PathVariable Integer soPTT) {
        return ApiResponse.<PhieuThuTienPhatResponse>builder()
                .code(1000)
                .message("Lấy thông tin phiếu thu tiền phạt thành công")
                .result(phieuThuTienPhatService.getById(soPTT))
                .build();
    }

    @PutMapping("/{soPTT}")
    public ApiResponse<PhieuThuTienPhatResponse> update(@PathVariable Integer soPTT,
            @RequestBody PhieuThuTienPhatUpdateRequest request) {
        return ApiResponse.<PhieuThuTienPhatResponse>builder()
                .code(1000)
                .message("Cập nhật phiếu thu tiền phạt thành công")
                .result(phieuThuTienPhatService.update(soPTT, request))
                .build();
    }

    @DeleteMapping("/{soPTT}")
    public ApiResponse<String> delete(@PathVariable Integer soPTT) {
        phieuThuTienPhatService.delete(soPTT);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa phiếu thu tiền phạt thành công")
                .result("Xóa phiếu thu tiền phạt thành công")
                .build();
    }
}