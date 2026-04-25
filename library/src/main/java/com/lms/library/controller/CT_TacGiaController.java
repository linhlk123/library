package com.lms.library.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.response.CT_TacGiaResponse;
import com.lms.library.service.CT_TacGiaService;
import lombok.RequiredArgsConstructor;
import com.lms.library.dto.request.CT_TacGiaCreationRequest;

@RequiredArgsConstructor 
@RestController 
@RequestMapping("/api/ct_tacgia")


public class CT_TacGiaController {

    private final CT_TacGiaService ctTacGiaService;

    @PostMapping
    public CT_TacGiaResponse createCT_TacGia(@RequestBody CT_TacGiaCreationRequest request) {
        return ctTacGiaService.createCT_TacGia(request);
    }

    @GetMapping
    public List<CT_TacGiaResponse> getAllCtTacGia() {
        return ctTacGiaService.getAllCT_TacGia();
    }

    @GetMapping("/dausach/{maDauSach}")
    public List<CT_TacGiaResponse> getByDauSach(@PathVariable Integer maDauSach) {
        return ctTacGiaService.getByDauSach(maDauSach);
    }

    @GetMapping("/tacgia/{maTacGia}")
    public List<CT_TacGiaResponse> getByTacGia(@PathVariable Integer maTacGia) {
        return ctTacGiaService.getByTacGia(maTacGia);
    }

    @DeleteMapping("/{maDauSach}/{maTacGia}")
    public String deleteCtTacGia(
            @PathVariable Integer maDauSach,
            @PathVariable Integer maTacGia) {
        ctTacGiaService.deleteCtTacGia(maDauSach, maTacGia);
        return "Xóa tác giả khỏi đầu sách thành công";
    }
}