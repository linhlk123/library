package com.lms.library.controller;

import com.lms.library.dto.request.LoaiDocGiaRequest;
import com.lms.library.dto.response.LoaiDocGiaResponse;
import com.lms.library.service.LoaiDocGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loaidocgia")
@CrossOrigin("*")
public class LoaiDocGiaController {

    @Autowired
    private LoaiDocGiaService loaiDocGiaService;

    // GET ALL
    @GetMapping
    public List<LoaiDocGiaResponse> getAllLoaiDocGia() {
        return loaiDocGiaService.getAllLoaiDocGia();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public LoaiDocGiaResponse getLoaiDocGiaById(@PathVariable Integer id) {
        return loaiDocGiaService.getLoaiDocGiaById(id);
    }

    // CREATE
    @PostMapping
    public LoaiDocGiaResponse createLoaiDocGia(@RequestBody LoaiDocGiaRequest request) {
        return loaiDocGiaService.createLoaiDocGia(request);
    }

    // UPDATE
    @PutMapping("/{id}")
    public LoaiDocGiaResponse updateLoaiDocGia(@PathVariable Integer id,
            @RequestBody LoaiDocGiaRequest request) {
        return loaiDocGiaService.updateLoaiDocGia(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteLoaiDocGia(@PathVariable Integer id) {
        loaiDocGiaService.deleteLoaiDocGia(id);
        return "Xóa loại độc giả thành công!";
    }
}