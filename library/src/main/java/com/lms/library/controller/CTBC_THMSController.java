package com.lms.library.controller;

import com.lms.library.dto.request.CTBC_THMSCreationRequest;
import com.lms.library.dto.request.CTBC_THMSUpdateRequest;
import com.lms.library.dto.response.CTBC_THMSResponse;
import com.lms.library.service.CTBC_THMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ctbc-thms")
@RequiredArgsConstructor
public class CTBC_THMSController {

    private final CTBC_THMSService service;

    @PostMapping
    public CTBC_THMSResponse create(@RequestBody CTBC_THMSCreationRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CTBC_THMSResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/baocao/{maBCTHMS}")
    public List<CTBC_THMSResponse> getByBaoCao(@PathVariable Integer maBCTHMS) {
        return service.getByBaoCao(maBCTHMS);
    }

    @GetMapping("/{maBCTHMS}/{maTheLoai}")
    public CTBC_THMSResponse getById(
            @PathVariable Integer maBCTHMS,
            @PathVariable Integer maTheLoai) {
        return service.getById(maBCTHMS, maTheLoai);
    }

    @PutMapping("/{maBCTHMS}/{maTheLoai}")
    public CTBC_THMSResponse update(
            @PathVariable Integer maBCTHMS,
            @PathVariable Integer maTheLoai,
            @RequestBody CTBC_THMSUpdateRequest request) {
        return service.update(maBCTHMS, maTheLoai, request);
    }

    @DeleteMapping("/{maBCTHMS}/{maTheLoai}")
    public String delete(
            @PathVariable Integer maBCTHMS,
            @PathVariable Integer maTheLoai) {
        service.delete(maBCTHMS, maTheLoai);
        return "Xóa chi tiết báo cáo tình hình mượn sách thành công";
    }
}