package com.lms.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.PhieuMuonTraCreationRequest;
import com.lms.library.dto.request.PhieuMuonTraRequestRequest;
import com.lms.library.dto.request.PhieuMuonTraUpdateRequest;
import com.lms.library.dto.response.PhieuMuonTraResponse;
import com.lms.library.dto.response.PhieuMuonTraResponseDTO;
import com.lms.library.service.PhieuMuonTraService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/phieumuontra")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuMuonTraController {

    PhieuMuonTraService phieuMuonTraService;
    @GetMapping("/count-pending")
        public ResponseEntity<Long> getPendingCount() {
            return ResponseEntity.ok(phieuMuonTraService.countPending()); 
    }
    /**
     * POST /api/phieumuontra/request
     * Tạo yêu cầu mượn sách từ phía người dùng (DocGia)
     * 
     * @param request Payload chứa tenDangNhap (username) và maCuonSach
     * @return PhieuMuonTraResponse - phiếu mượn với trangThai = PENDING
     */
    @PostMapping("/request")
    public ApiResponse<PhieuMuonTraResponse> createBorrowRequest(@RequestBody PhieuMuonTraRequestRequest request) {
        return ApiResponse.<PhieuMuonTraResponse>builder()
                .code(1000)
                .message("Tạo yêu cầu mượn sách thành công. Vui lòng chờ nhân viên xác nhận.")
                .result(phieuMuonTraService.createBorrowRequest(request))
                .build();
    }

    @PostMapping
    public ApiResponse<PhieuMuonTraResponse> create(@RequestBody PhieuMuonTraCreationRequest request) {
        return ApiResponse.<PhieuMuonTraResponse>builder()
                .code(1000)
                .message("Tạo phiếu mượn trả thành công")
                .result(phieuMuonTraService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PhieuMuonTraResponseDTO>> getAll() {
        return ApiResponse.<List<PhieuMuonTraResponseDTO>>builder()
                .code(1000)
                .message("Lấy danh sách phiếu mượn trả thành công")
                .result(phieuMuonTraService.getAll())
                .build();
    }

    @GetMapping("/docgia/{maDocGia}")
    public ApiResponse<List<PhieuMuonTraResponseDTO>> getByMaDocGia(@PathVariable String maDocGia) {
        return ApiResponse.<List<PhieuMuonTraResponseDTO>>builder()
                .code(1000)
                .message("Lấy danh sách phiếu mượn trả theo độc giả thành công")
                .result(phieuMuonTraService.getByMaDocGia(maDocGia))
                .build();
    }

    @GetMapping("/{soPhieu}")
    public ApiResponse<PhieuMuonTraResponse> getById(@PathVariable Integer soPhieu) {
        return ApiResponse.<PhieuMuonTraResponse>builder()
                .code(1000)
                .message("Lấy thông tin phiếu mượn trả thành công")
                .result(phieuMuonTraService.getById(soPhieu))
                .build();
    }

    @PutMapping("/{soPhieu}")
    public ApiResponse<PhieuMuonTraResponse> update(@PathVariable Integer soPhieu,
            @RequestBody PhieuMuonTraUpdateRequest request) {
        return ApiResponse.<PhieuMuonTraResponse>builder()
                .code(1000)
                .message("Cập nhật phiếu mượn trả thành công")
                .result(phieuMuonTraService.update(soPhieu, request))
                .build();
    }

    @DeleteMapping("/{soPhieu}")
    public ApiResponse<String> delete(@PathVariable Integer soPhieu) {
        phieuMuonTraService.delete(soPhieu);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa phiếu mượn trả thành công")
                .result("Xóa phiếu mượn trả thành công")
                .build();
    }
}