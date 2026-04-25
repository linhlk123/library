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
import com.lms.library.dto.request.DocGiaCreationRequest;
import com.lms.library.dto.request.DocGiaUpdateRequest;
import com.lms.library.dto.response.DocGiaResponse;
import com.lms.library.dto.response.MyReaderCardResponse;
import com.lms.library.service.DocGiaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/docgia")
@RequiredArgsConstructor
public class DocGiaController {

    private final DocGiaService docGiaService;

    @PostMapping
    public ApiResponse<DocGiaResponse> createDocGia(@RequestBody DocGiaCreationRequest request) {
        return ApiResponse.<DocGiaResponse>builder()
                .code(1000)
                .message("Tạo độc giả thành công")
                .result(docGiaService.createDocGia(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<DocGiaResponse>> getAllDocGia() {
        return ApiResponse.<List<DocGiaResponse>>builder()
                .code(1000)
                .message("Lấy danh sách độc giả thành công")
                .result(docGiaService.getAllDocGia())
                .build();
    }

    /**
     * GET /api/docgia/my-card
     * Lấy thông tin thẻ độc giả của người dùng hiện tại (đang đăng nhập)
     * Sử dụng SecurityContextHolder để xác định người dùng từ JWT Token
     * 
     * @return MyReaderCardResponse chứa thông tin thẻ độc giả
     */
    @GetMapping("/my-card")
    public ApiResponse<MyReaderCardResponse> getMyReaderCard() {
        return ApiResponse.<MyReaderCardResponse>builder()
                .code(1000)
                .message("Lấy thông tin thẻ độc giả thành công")
                .result(docGiaService.getMyReaderCard())
                .build();
    }

    @GetMapping("/{maDocGia}")
    public ApiResponse<DocGiaResponse> getDocGiaById(@PathVariable String maDocGia) {
        return ApiResponse.<DocGiaResponse>builder()
                .code(1000)
                .message("Lấy thông tin độc giả thành công")
                .result(docGiaService.getDocGiaById(maDocGia))
                .build();
    }

    @PutMapping("/{maDocGia}")
    public ApiResponse<DocGiaResponse> updateDocGia(
            @PathVariable String maDocGia,
            @RequestBody DocGiaUpdateRequest request) {
        return ApiResponse.<DocGiaResponse>builder()
                .code(1000)
                .message("Cập nhật độc giả thành công")
                .result(docGiaService.updateDocGia(maDocGia, request))
                .build();
    }

    @DeleteMapping("/{maDocGia}")
    public ApiResponse<String> deleteDocGia(@PathVariable String maDocGia) {
        docGiaService.deleteDocGia(maDocGia);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa độc giả thành công")
                .result("Xóa độc giả thành công")
                .build();
    }
}