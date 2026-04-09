package com.lms.library.controller;

import com.lms.library.dto.request.PhieuMuonTraCreationRequest;
import com.lms.library.dto.request.PhieuMuonTraUpdateRequest;
import com.lms.library.dto.response.PhieuMuonTraResponse;
import com.lms.library.service.PhieuMuonTraService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieumuontra")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuMuonTraController {

    PhieuMuonTraService phieuMuonTraService;

    @PostMapping
    public PhieuMuonTraResponse create(@RequestBody PhieuMuonTraCreationRequest request) {
        return phieuMuonTraService.create(request);
    }

    @GetMapping
    public List<PhieuMuonTraResponse> getAll() {
        return phieuMuonTraService.getAll();
    }

    @GetMapping("/{soPhieu}")
    public PhieuMuonTraResponse getById(@PathVariable Integer soPhieu) {
        return phieuMuonTraService.getById(soPhieu);
    }

    @PutMapping("/{soPhieu}")
    public PhieuMuonTraResponse update(@PathVariable Integer soPhieu,
            @RequestBody PhieuMuonTraUpdateRequest request) {
        return phieuMuonTraService.update(soPhieu, request);
    }

    @DeleteMapping("/{soPhieu}")
    public String delete(@PathVariable Integer soPhieu) {
        phieuMuonTraService.delete(soPhieu);
        return "Xóa phiếu mượn trả thành công";
    }
}