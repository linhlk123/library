package com.lms.library.service;

import com.lms.library.dto.request.LoaiDocGiaRequest;
import com.lms.library.dto.response.LoaiDocGiaResponse;
import com.lms.library.entity.LoaiDocGia;
import com.lms.library.repository.LoaiDocGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoaiDocGiaService {

    @Autowired
    private LoaiDocGiaRepository loaiDocGiaRepository;

    // Lấy tất cả
    public List<LoaiDocGiaResponse> getAllLoaiDocGia() {
        List<LoaiDocGia> list = loaiDocGiaRepository.findAll();
        return list.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Lấy theo ID
    public LoaiDocGiaResponse getLoaiDocGiaById(Integer id) {
        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại độc giả với mã: " + id));

        return mapToResponse(loaiDocGia);
    }

    // Thêm mới
    public LoaiDocGiaResponse createLoaiDocGia(LoaiDocGiaRequest request) {
        if (request.getTenLoaiDocGia() == null || request.getTenLoaiDocGia().trim().isEmpty()) {
            throw new RuntimeException("Tên loại độc giả không được để trống");
        }

        if (loaiDocGiaRepository.existsByTenLoaiDocGia(request.getTenLoaiDocGia().trim())) {
            throw new RuntimeException("Tên loại độc giả đã tồn tại");
        }

        LoaiDocGia loaiDocGia = new LoaiDocGia();
        loaiDocGia.setTenLoaiDocGia(request.getTenLoaiDocGia().trim());

        LoaiDocGia saved = loaiDocGiaRepository.save(loaiDocGia);
        return mapToResponse(saved);
    }

    // Cập nhật
    public LoaiDocGiaResponse updateLoaiDocGia(Integer id, LoaiDocGiaRequest request) {
        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại độc giả với mã: " + id));

        if (request.getTenLoaiDocGia() == null || request.getTenLoaiDocGia().trim().isEmpty()) {
            throw new RuntimeException("Tên loại độc giả không được để trống");
        }

        loaiDocGia.setTenLoaiDocGia(request.getTenLoaiDocGia().trim());
        LoaiDocGia updated = loaiDocGiaRepository.save(loaiDocGia);

        return mapToResponse(updated);
    }

    // Xóa
    public void deleteLoaiDocGia(Integer id) {
        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại độc giả với mã: " + id));

        loaiDocGiaRepository.delete(loaiDocGia);
    }

    // Map Entity -> Response
    private LoaiDocGiaResponse mapToResponse(LoaiDocGia loaiDocGia) {
        return new LoaiDocGiaResponse(
                loaiDocGia.getMaLoaiDocGia(),
                loaiDocGia.getTenLoaiDocGia());
    }
}