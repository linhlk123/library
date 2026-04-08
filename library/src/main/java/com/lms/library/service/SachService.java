package com.lms.library.service;

import com.lms.library.dto.request.SachCreationRequest;
import com.lms.library.dto.request.SachUpdateRequest;
import com.lms.library.dto.response.SachResponse;
import com.lms.library.entity.DauSach;
import com.lms.library.entity.Sach;
import com.lms.library.mapper.SachMapper;
import com.lms.library.repository.DauSachRepository;
import com.lms.library.repository.SachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SachService {

    SachRepository sachRepository;
    DauSachRepository dauSachRepository;
    SachMapper sachMapper;

    public SachResponse createSach(SachCreationRequest request) {
        DauSach dauSach = dauSachRepository.findById(request.getMaDauSach())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        Sach sach = Sach.builder()
                .dauSach(dauSach)
                .nhaXuatBan(request.getNhaXuatBan())
                .namXuatBan(request.getNamXuatBan())
                .soLuong(request.getSoLuong() != null ? request.getSoLuong() : 0)
                .giaTien(request.getGiaTien())
                .build();

        return sachMapper.toSachResponse(sachRepository.save(sach));
    }

    public List<SachResponse> getAllSach() {
        return sachRepository.findAll()
                .stream()
                .map(sachMapper::toSachResponse)
                .toList();
    }

    public SachResponse getSachById(Integer maSach) {
        Sach sach = sachRepository.findById(maSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

        return sachMapper.toSachResponse(sach);
    }

    public List<SachResponse> getSachByDauSach(Integer maDauSach) {
        if (!dauSachRepository.existsById(maDauSach)) {
            throw new RuntimeException("Không tìm thấy đầu sách");
        }

        return sachRepository.findByDauSach_MaDauSach(maDauSach)
                .stream()
                .map(sachMapper::toSachResponse)
                .toList();
    }

    public SachResponse updateSach(Integer maSach, SachUpdateRequest request) {
        Sach sach = sachRepository.findById(maSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

        if (request.getMaDauSach() != null) {
            DauSach dauSach = dauSachRepository.findById(request.getMaDauSach())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));
            sach.setDauSach(dauSach);
        }

        if (request.getNhaXuatBan() != null) {
            sach.setNhaXuatBan(request.getNhaXuatBan());
        }

        if (request.getNamXuatBan() != null) {
            sach.setNamXuatBan(request.getNamXuatBan());
        }

        if (request.getSoLuong() != null) {
            sach.setSoLuong(request.getSoLuong());
        }

        if (request.getGiaTien() != null) {
            sach.setGiaTien(request.getGiaTien());
        }

        return sachMapper.toSachResponse(sachRepository.save(sach));
    }

    public void deleteSach(Integer maSach) {
        if (!sachRepository.existsById(maSach)) {
            throw new RuntimeException("Không tìm thấy sách");
        }

        sachRepository.deleteById(maSach);
    }
}