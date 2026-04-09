package com.lms.library.controller;

import com.lms.library.dto.request.PhieuThuTienPhatCreationRequest;
import com.lms.library.dto.request.PhieuThuTienPhatUpdateRequest;
import com.lms.library.dto.response.PhieuThuTienPhatResponse;
import com.lms.library.service.PhieuThuTienPhatService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieuthutienphat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuThuTienPhatController {

    PhieuThuTienPhatService phieuThuTienPhatService;

    @PostMapping
    public PhieuThuTienPhatResponse create(@RequestBody PhieuThuTienPhatCreationRequest request) {
        return phieuThuTienPhatService.create(request);
    }

    @GetMapping
    public List<PhieuThuTienPhatResponse> getAll() {
        return phieuThuTienPhatService.getAll();
    }

    @GetMapping("/{soPTT}")
    public PhieuThuTienPhatResponse getById(@PathVariable Integer soPTT) {
        return phieuThuTienPhatService.getById(soPTT);
    }

    @PutMapping("/{soPTT}")
    public PhieuThuTienPhatResponse update(@PathVariable Integer soPTT,
            @RequestBody PhieuThuTienPhatUpdateRequest request) {
        return phieuThuTienPhatService.update(soPTT, request);
    }

    @DeleteMapping("/{soPTT}")
    public String delete(@PathVariable Integer soPTT) {
        phieuThuTienPhatService.delete(soPTT);
        return "Xóa phiếu thu tiền phạt thành công";
    }
}