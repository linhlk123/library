package com.lms.library.controller;

import com.lms.library.dto.request.TheLoaiCreationRequest;
import com.lms.library.dto.request.TheLoaiUpdateRequest;
import com.lms.library.dto.response.TheLoaiResponse;
import com.lms.library.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theloai")
@RequiredArgsConstructor
public class TheLoaiController {

    private final TheLoaiService theLoaiService;

    @PostMapping
    public TheLoaiResponse createTheLoai(@RequestBody TheLoaiCreationRequest request) {
        return theLoaiService.createTheLoai(request);
    }

    @GetMapping
    public List<TheLoaiResponse> getAllTheLoai() {
        return theLoaiService.getAllTheLoai();
    }

    @GetMapping("/{maTheLoai}")
    public TheLoaiResponse getTheLoaiById(@PathVariable Integer maTheLoai) {
        return theLoaiService.getTheLoaiById(maTheLoai);
    }

    @PutMapping("/{maTheLoai}")
    public TheLoaiResponse updateTheLoai(
            @PathVariable Integer maTheLoai,
            @RequestBody TheLoaiUpdateRequest request) {
        return theLoaiService.updateTheLoai(maTheLoai, request);
    }

    @DeleteMapping("/{maTheLoai}")
    public String deleteTheLoai(@PathVariable Integer maTheLoai) {
        theLoaiService.deleteTheLoai(maTheLoai);
        return "Xóa thể loại thành công";
    }
}