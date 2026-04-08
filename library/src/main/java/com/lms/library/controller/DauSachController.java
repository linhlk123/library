package com.lms.library.controller;

import com.lms.library.dto.request.DauSachCreationRequest;
import com.lms.library.dto.request.DauSachUpdateRequest;
import com.lms.library.dto.response.DauSachResponse;
import com.lms.library.service.DauSachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dausach")
@RequiredArgsConstructor
public class DauSachController {

    private final DauSachService dauSachService;

    @PostMapping
    public DauSachResponse createDauSach(@RequestBody DauSachCreationRequest request) {
        return dauSachService.createDauSach(request);
    }

    @GetMapping
    public List<DauSachResponse> getAllDauSach() {
        return dauSachService.getAllDauSach();
    }

    @GetMapping("/{maDauSach}")
    public DauSachResponse getDauSachById(@PathVariable Integer maDauSach) {
        return dauSachService.getDauSachById(maDauSach);
    }

    @GetMapping("/theloai/{maTheLoai}")
    public List<DauSachResponse> getDauSachByTheLoai(@PathVariable Integer maTheLoai) {
        return dauSachService.getDauSachByTheLoai(maTheLoai);
    }

    @PutMapping("/{maDauSach}")
    public DauSachResponse updateDauSach(
            @PathVariable Integer maDauSach,
            @RequestBody DauSachUpdateRequest request) {
        return dauSachService.updateDauSach(maDauSach, request);
    }

    @DeleteMapping("/{maDauSach}")
    public String deleteDauSach(@PathVariable Integer maDauSach) {
        dauSachService.deleteDauSach(maDauSach);
        return "Xóa đầu sách thành công";
    }
}