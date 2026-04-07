package com.lms.library.controller;

import com.lms.library.dto.request.DocGiaCreationRequest;
import com.lms.library.dto.request.DocGiaUpdateRequest;
import com.lms.library.dto.response.DocGiaResponse;
import com.lms.library.service.DocGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docgia")
@RequiredArgsConstructor
public class DocGiaController {

    private final DocGiaService docGiaService;

    @PostMapping
    public DocGiaResponse createDocGia(@RequestBody DocGiaCreationRequest request) {
        return docGiaService.createDocGia(request);
    }

    @GetMapping
    public List<DocGiaResponse> getAllDocGia() {
        return docGiaService.getAllDocGia();
    }

    @GetMapping("/{maDocGia}")
    public DocGiaResponse getDocGiaById(@PathVariable Integer maDocGia) {
        return docGiaService.getDocGiaById(maDocGia);
    }

    @PutMapping("/{maDocGia}")
    public DocGiaResponse updateDocGia(
            @PathVariable Integer maDocGia,
            @RequestBody DocGiaUpdateRequest request) {
        return docGiaService.updateDocGia(maDocGia, request);
    }

    @DeleteMapping("/{maDocGia}")
    public String deleteDocGia(@PathVariable Integer maDocGia) {
        docGiaService.deleteDocGia(maDocGia);
        return "Xóa độc giả thành công";
    }
}