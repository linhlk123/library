package com.lms.library.controller;

import com.lms.library.dto.request.TacGiaCreationRequest;
import com.lms.library.dto.request.TacGiaUpdateRequest;
import com.lms.library.dto.response.TacGiaResponse;
import com.lms.library.service.TacGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tacgia")
@RequiredArgsConstructor
public class TacGiaController {

    private final TacGiaService tacGiaService;

    @PostMapping
    public TacGiaResponse createTacGia(@RequestBody TacGiaCreationRequest request) {
        return tacGiaService.createTacGia(request);
    }

    @GetMapping
    public List<TacGiaResponse> getAllTacGia() {
        return tacGiaService.getAllTacGia();
    }

    @GetMapping("/{maTacGia}")
    public TacGiaResponse getTacGiaById(@PathVariable Integer maTacGia) {
        return tacGiaService.getTacGiaById(maTacGia);
    }

    @PutMapping("/{maTacGia}")
    public TacGiaResponse updateTacGia(
            @PathVariable Integer maTacGia,
            @RequestBody TacGiaUpdateRequest request) {
        return tacGiaService.updateTacGia(maTacGia, request);
    }

    @DeleteMapping("/{maTacGia}")
    public String deleteTacGia(@PathVariable Integer maTacGia) {
        tacGiaService.deleteTacGia(maTacGia);
        return "Xóa tác giả thành công";
    }
}