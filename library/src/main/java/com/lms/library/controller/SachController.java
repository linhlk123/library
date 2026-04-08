package com.lms.library.controller;

import com.lms.library.dto.request.SachCreationRequest;
import com.lms.library.dto.request.SachUpdateRequest;
import com.lms.library.dto.response.SachResponse;
import com.lms.library.service.SachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sach")
@RequiredArgsConstructor
public class SachController {

    private final SachService sachService;

    @PostMapping
    public SachResponse createSach(@RequestBody SachCreationRequest request) {
        return sachService.createSach(request);
    }

    @GetMapping
    public List<SachResponse> getAllSach() {
        return sachService.getAllSach();
    }

    @GetMapping("/{maSach}")
    public SachResponse getSachById(@PathVariable Integer maSach) {
        return sachService.getSachById(maSach);
    }

    @GetMapping("/dausach/{maDauSach}")
    public List<SachResponse> getSachByDauSach(@PathVariable Integer maDauSach) {
        return sachService.getSachByDauSach(maDauSach);
    }

    @PutMapping("/{maSach}")
    public SachResponse updateSach(
            @PathVariable Integer maSach,
            @RequestBody SachUpdateRequest request) {
        return sachService.updateSach(maSach, request);
    }

    @DeleteMapping("/{maSach}")
    public String deleteSach(@PathVariable Integer maSach) {
        sachService.deleteSach(maSach);
        return "Xóa sách thành công";
    }
}