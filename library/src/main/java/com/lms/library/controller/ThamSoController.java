package com.lms.library.controller;

import com.lms.library.dto.request.ThamSoCreationRequest;
import com.lms.library.dto.request.ThamSoUpdateRequest;
import com.lms.library.dto.response.ThamSoResponse;
import com.lms.library.service.ThamSoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thamso")
@RequiredArgsConstructor
public class ThamSoController {

    private final ThamSoService thamSoService;

    @PostMapping
    public ThamSoResponse createThamSo(@RequestBody ThamSoCreationRequest request) {
        return thamSoService.createThamSo(request);
    }

    @GetMapping
    public List<ThamSoResponse> getAllThamSo() {
        return thamSoService.getAllThamSo();
    }

    @GetMapping("/{tenThamSo}")
    public ThamSoResponse getThamSoById(@PathVariable String tenThamSo) {
        return thamSoService.getThamSoById(tenThamSo);
    }

    @PutMapping("/{tenThamSo}")
    public ThamSoResponse updateThamSo(
            @PathVariable String tenThamSo,
            @RequestBody ThamSoUpdateRequest request) {
        return thamSoService.updateThamSo(tenThamSo, request);
    }

    @DeleteMapping("/{tenThamSo}")
    public String deleteThamSo(@PathVariable String tenThamSo) {
        thamSoService.deleteThamSo(tenThamSo);
        return "Xóa tham số thành công";
    }
}