package com.lms.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.LoaiDocGiaRequest;
import com.lms.library.dto.response.LoaiDocGiaResponse;
import com.lms.library.service.LoaiDocGiaService;

@RestController
@RequestMapping("/api/loaidocgia")
@CrossOrigin("*")
public class LoaiDocGiaController {

    @Autowired
    private LoaiDocGiaService loaiDocGiaService;

    // GET ALL
    @GetMapping
    public ApiResponse<List<LoaiDocGiaResponse>> getAllLoaiDocGia() {
        return ApiResponse.<List<LoaiDocGiaResponse>>builder()
                .code(1000)
                .message("Lấy danh sách loại độc giả thành công")
                .result(loaiDocGiaService.getAllLoaiDocGia())
                .build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<LoaiDocGiaResponse> getLoaiDocGiaById(@PathVariable Integer id) {
        return ApiResponse.<LoaiDocGiaResponse>builder()
                .code(1000)
                .message("Lấy thông tin loại độc giả thành công")
                .result(loaiDocGiaService.getLoaiDocGiaById(id))
                .build();
    }

    // CREATE
    @PostMapping
    public ApiResponse<LoaiDocGiaResponse> createLoaiDocGia(@RequestBody LoaiDocGiaRequest request) {
        return ApiResponse.<LoaiDocGiaResponse>builder()
                .code(1000)
                .message("Tạo loại độc giả thành công")
                .result(loaiDocGiaService.createLoaiDocGia(request))
                .build();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<LoaiDocGiaResponse> updateLoaiDocGia(@PathVariable Integer id,
            @RequestBody LoaiDocGiaRequest request) {
        return ApiResponse.<LoaiDocGiaResponse>builder()
                .code(1000)
                .message("Cập nhật loại độc giả thành công")
                .result(loaiDocGiaService.updateLoaiDocGia(id, request))
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteLoaiDocGia(@PathVariable Integer id) {
        loaiDocGiaService.deleteLoaiDocGia(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa loại độc giả thành công")
                .result("Xóa loại độc giả thành công!")
                .build();
    }
}