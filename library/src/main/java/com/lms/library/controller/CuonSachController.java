package com.lms.library.controller;

import com.lms.library.dto.request.CuonSachCreationRequest;
import com.lms.library.dto.request.CuonSachUpdateRequest;
import com.lms.library.dto.response.CuonSachResponse;
import com.lms.library.service.CuonSachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuonsach")
@RequiredArgsConstructor
public class CuonSachController {

    private final CuonSachService cuonSachService;

    @PostMapping
    public CuonSachResponse createCuonSach(@RequestBody CuonSachCreationRequest request) {
        return cuonSachService.createCuonSach(request);
    }

    @GetMapping
    public List<CuonSachResponse> getAllCuonSach() {
        return cuonSachService.getAllCuonSach();
    }

    @GetMapping("/{maCuonSach}")
    public CuonSachResponse getCuonSachById(@PathVariable Integer maCuonSach) {
        return cuonSachService.getCuonSachById(maCuonSach);
    }

    @GetMapping("/sach/{maSach}")
    public List<CuonSachResponse> getCuonSachBySach(@PathVariable Integer maSach) {
        return cuonSachService.getCuonSachBySach(maSach);
    }

    @PutMapping("/{maCuonSach}")
    public CuonSachResponse updateCuonSach(
            @PathVariable Integer maCuonSach,
            @RequestBody CuonSachUpdateRequest request) {
        return cuonSachService.updateCuonSach(maCuonSach, request);
    }

    @DeleteMapping("/{maCuonSach}")
    public String deleteCuonSach(@PathVariable Integer maCuonSach) {
        cuonSachService.deleteCuonSach(maCuonSach);
        return "Xóa cuốn sách thành công";
    }
}