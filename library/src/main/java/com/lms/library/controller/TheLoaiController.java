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
import com.lms.library.dto.request.TheLoaiCreationRequest;
import com.lms.library.dto.request.TheLoaiUpdateRequest;
import com.lms.library.dto.response.TheLoaiResponse;
import com.lms.library.service.TheLoaiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/theloai")
@RequiredArgsConstructor
public class TheLoaiController {

    private final TheLoaiService theLoaiService;

    @PostMapping
    public ApiResponse<TheLoaiResponse> createTheLoai(@RequestBody TheLoaiCreationRequest request) {
        return ApiResponse.<TheLoaiResponse>builder()
                .code(1000)
                .message("Tạo thể loại thành công")
                .result(theLoaiService.createTheLoai(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<TheLoaiResponse>> getAllTheLoai() {
        return ApiResponse.<List<TheLoaiResponse>>builder()
                .code(1000)
                .message("Lấy danh sách thể loại thành công")
                .result(theLoaiService.getAllTheLoai())
                .build();
    }

    @GetMapping("/{maTheLoai}")
    public ApiResponse<TheLoaiResponse> getTheLoaiById(@PathVariable Integer maTheLoai) {
        return ApiResponse.<TheLoaiResponse>builder()
                .code(1000)
                .message("Lấy thông tin thể loại thành công")
                .result(theLoaiService.getTheLoaiById(maTheLoai))
                .build();
    }

    @PutMapping("/{maTheLoai}")
    public ApiResponse<TheLoaiResponse> updateTheLoai(
            @PathVariable Integer maTheLoai,
            @RequestBody TheLoaiUpdateRequest request) {
        return ApiResponse.<TheLoaiResponse>builder()
                .code(1000)
                .message("Cập nhật thể loại thành công")
                .result(theLoaiService.updateTheLoai(maTheLoai, request))
                .build();
    }

    @DeleteMapping("/{maTheLoai}")
    public ApiResponse<String> deleteTheLoai(@PathVariable Integer maTheLoai) {
        theLoaiService.deleteTheLoai(maTheLoai);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa thể loại thành công")
                .result("Xóa thể loại thành công")
                .build();
    }
}