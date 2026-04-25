package com.lms.library.controller;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.CTBC_THMSCreationRequest;
import com.lms.library.dto.request.CTBC_THMSUpdateRequest;
import com.lms.library.dto.response.CTBC_THMSResponse;
import com.lms.library.service.CTBC_THMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ctbc-thms")
@RequiredArgsConstructor
public class CTBC_THMSController {

    private final CTBC_THMSService service;

    @PostMapping
    public ApiResponse<CTBC_THMSResponse> create(@RequestBody CTBC_THMSCreationRequest request) {
        return ApiResponse.<CTBC_THMSResponse>builder()
                .code(1000)
                .message("Tạo chi tiết báo cáo thành công")
                .result(service.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CTBC_THMSResponse>> getAll() {
        return ApiResponse.<List<CTBC_THMSResponse>>builder()
                .code(1000)
                .message("Lấy danh sách chi tiết báo cáo thành công")
                .result(service.getAll())
                .build();
    }

    @GetMapping("/baocao/{maBCTHMS}")
    public ApiResponse<List<CTBC_THMSResponse>> getByBaoCao(@PathVariable Integer maBCTHMS) {
        return ApiResponse.<List<CTBC_THMSResponse>>builder()
                .code(1000)
                .message("Lấy chi tiết báo cáo thành công")
                .result(service.getByBaoCao(maBCTHMS))
                .build();
    }

    @GetMapping("/{maBCTHMS}/{maTheLoai}")
    public ApiResponse<CTBC_THMSResponse> getById(
            @PathVariable Integer maBCTHMS,
            @PathVariable Integer maTheLoai) {
        return ApiResponse.<CTBC_THMSResponse>builder()
                .code(1000)
                .message("Lấy thông tin chi tiết báo cáo thành công")
                .result(service.getById(maBCTHMS, maTheLoai))
                .build();
    }

    @PutMapping("/{maBCTHMS}/{maTheLoai}")
    public ApiResponse<CTBC_THMSResponse> update(
            @PathVariable Integer maBCTHMS,
            @PathVariable Integer maTheLoai,
            @RequestBody CTBC_THMSUpdateRequest request) {
        return ApiResponse.<CTBC_THMSResponse>builder()
                .code(1000)
                .message("Cập nhật chi tiết báo cáo thành công")
                .result(service.update(maBCTHMS, maTheLoai, request))
                .build();
    }

    @DeleteMapping("/{maBCTHMS}/{maTheLoai}")
    public ApiResponse<String> delete(
            @PathVariable Integer maBCTHMS,
            @PathVariable Integer maTheLoai) {
        service.delete(maBCTHMS, maTheLoai);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa chi tiết báo cáo thành công")
                .result("Xóa chi tiết báo cáo tình hình mượn sách thành công")
                .build();
    }
}