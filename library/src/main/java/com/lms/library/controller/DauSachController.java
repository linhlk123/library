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
import org.springframework.web.multipart.MultipartFile;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.DauSachCreationRequest;
import com.lms.library.dto.request.DauSachUpdateRequest;
import com.lms.library.dto.response.DauSachResponse;
import com.lms.library.service.CloudinaryService;
import com.lms.library.service.DauSachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dausach")
@RequiredArgsConstructor
public class DauSachController {

    private final DauSachService dauSachService;
    private final CloudinaryService cloudinaryService;

    @PostMapping
    public ApiResponse<DauSachResponse> createDauSach(@RequestBody DauSachCreationRequest request) {
        return ApiResponse.<DauSachResponse>builder()
                .code(1000)
                .message("Tạo đầu sách thành công")
                .result(dauSachService.createDauSach(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<DauSachResponse>> getAllDauSach() {
        return ApiResponse.<List<DauSachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách đầu sách thành công")
                .result(dauSachService.getAllDauSach())
                .build();
    }

    @GetMapping("/{maDauSach}")
    public ApiResponse<DauSachResponse> getDauSachById(@PathVariable Integer maDauSach) {
        return ApiResponse.<DauSachResponse>builder()
                .code(1000)
                .message("Lấy thông tin đầu sách thành công")
                .result(dauSachService.getDauSachById(maDauSach))
                .build();
    }

    @GetMapping("/theloai/{maTheLoai}")
    public ApiResponse<List<DauSachResponse>> getDauSachByTheLoai(@PathVariable Integer maTheLoai) {
        return ApiResponse.<List<DauSachResponse>>builder()
                .code(1000)
                .message("Lấy danh sách đầu sách theo thể loại thành công")
                .result(dauSachService.getDauSachByTheLoai(maTheLoai))
                .build();
    }

    @PutMapping("/{maDauSach}")
    public ApiResponse<DauSachResponse> updateDauSach(
            @PathVariable Integer maDauSach,
            @RequestBody DauSachUpdateRequest request) {
        return ApiResponse.<DauSachResponse>builder()
                .code(1000)
                .message("Cập nhật đầu sách thành công")
                .result(dauSachService.updateDauSach(maDauSach, request))
                .build();
    }

    @DeleteMapping("/{maDauSach}")
    public ApiResponse<String> deleteDauSach(@PathVariable Integer maDauSach) {
        dauSachService.deleteDauSach(maDauSach);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa đầu sách thành công")
                .result("Xóa đầu sách thành công")
                .build();
    }

    @DeleteMapping("/batch")
    public ApiResponse<String> deleteBatch(@RequestBody List<Integer> ids) {
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa đầu sách thành công")
                .result(dauSachService.deleteMultiple(ids))
                .build();
    }

    @PostMapping("/upload-image")
    public ApiResponse<String> uploadCoverImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadImage(file);
            return ApiResponse.<String>builder()
                    .code(1000)
                    .message("Upload ảnh thành công")
                    .result(imageUrl)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }
}