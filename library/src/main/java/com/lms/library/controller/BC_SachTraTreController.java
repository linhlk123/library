package com.lms.library.controller;

import com.lms.library.dto.request.BC_SachTraTreCreationRequest;
import com.lms.library.dto.request.BC_SachTraTreUpdateRequest;
import com.lms.library.dto.response.BC_SachTraTreResponse;
import com.lms.library.service.BC_SachTraTreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bc_sachtratre")
@RequiredArgsConstructor
public class BC_SachTraTreController {

    private final BC_SachTraTreService bcSachTraTreService;

    @PostMapping
    public BC_SachTraTreResponse create(@RequestBody BC_SachTraTreCreationRequest request) {
        return bcSachTraTreService.create(request);
    }

    @GetMapping
    public List<BC_SachTraTreResponse> getAll() {
        return bcSachTraTreService.getAll();
    }

    @GetMapping("/ngay/{ngay}")
    public List<BC_SachTraTreResponse> getByNgay(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay) {
        return bcSachTraTreService.getByNgay(ngay);
    }

    @GetMapping("/cuonsach/{maCuonSach}")
    public List<BC_SachTraTreResponse> getByCuonSach(@PathVariable Integer maCuonSach) {
        return bcSachTraTreService.getByCuonSach(maCuonSach);
    }

    @GetMapping("/{ngay}/{maCuonSach}")
    public BC_SachTraTreResponse getById(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @PathVariable Integer maCuonSach) {
        return bcSachTraTreService.getById(ngay, maCuonSach);
    }

    @PutMapping("/{ngay}/{maCuonSach}")
    public BC_SachTraTreResponse update(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @PathVariable Integer maCuonSach,
            @RequestBody BC_SachTraTreUpdateRequest request) {
        return bcSachTraTreService.update(ngay, maCuonSach, request);
    }

    @DeleteMapping("/{ngay}/{maCuonSach}")
    public String delete(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @PathVariable Integer maCuonSach) {
        bcSachTraTreService.delete(ngay, maCuonSach);
        return "Xóa bản ghi thành công";
    }
}