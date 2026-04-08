package com.lms.library.controller;

import com.lms.library.dto.request.BaoCaoTinhHinhMuonSachCreationRequest;
import com.lms.library.dto.request.BaoCaoTinhHinhMuonSachUpdateRequest;
import com.lms.library.dto.response.BaoCaoTinhHinhMuonSachResponse;
import com.lms.library.service.BaoCaoTinhHinhMuonSachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bctinhhinhmuonsach")
@RequiredArgsConstructor
public class BaoCaoTinhHinhMuonSachController {

    private final BaoCaoTinhHinhMuonSachService service;

    @PostMapping
    public BaoCaoTinhHinhMuonSachResponse create(@RequestBody BaoCaoTinhHinhMuonSachCreationRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<BaoCaoTinhHinhMuonSachResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{maBCTHMS}")
    public BaoCaoTinhHinhMuonSachResponse getById(@PathVariable Integer maBCTHMS) {
        return service.getById(maBCTHMS);
    }

    @PutMapping("/{maBCTHMS}")
    public BaoCaoTinhHinhMuonSachResponse update(
            @PathVariable Integer maBCTHMS,
            @RequestBody BaoCaoTinhHinhMuonSachUpdateRequest request) {
        return service.update(maBCTHMS, request);
    }

    @DeleteMapping("/{maBCTHMS}")
    public String delete(@PathVariable Integer maBCTHMS) {
        service.delete(maBCTHMS);
        return "Xóa báo cáo tình hình mượn sách thành công";
    }
}