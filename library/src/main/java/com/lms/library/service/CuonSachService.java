package com.lms.library.service;

import com.lms.library.dto.request.CuonSachCreationRequest;
import com.lms.library.dto.request.CuonSachUpdateRequest;
import com.lms.library.dto.response.CuonSachResponse;
import com.lms.library.entity.CuonSach;
import com.lms.library.entity.Sach;
import com.lms.library.mapper.CuonSachMapper;
import com.lms.library.repository.CuonSachRepository;
import com.lms.library.repository.SachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CuonSachService {

    CuonSachRepository cuonSachRepository;
    SachRepository sachRepository;
    CuonSachMapper cuonSachMapper;

    public CuonSachResponse createCuonSach(CuonSachCreationRequest request) {
        Sach sach = sachRepository.findById(request.getMaSach())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

        CuonSach cuonSach = CuonSach.builder()
                .sach(sach)
                .tinhTrang(request.getTinhTrang())
                .build();

        return cuonSachMapper.toCuonSachResponse(cuonSachRepository.save(cuonSach));
    }

    public List<CuonSachResponse> getAllCuonSach() {
        return cuonSachRepository.findAll()
                .stream()
                .map(cuonSachMapper::toCuonSachResponse)
                .toList();
    }

    public CuonSachResponse getCuonSachById(Integer maCuonSach) {
        CuonSach cuonSach = cuonSachRepository.findById(maCuonSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuốn sách"));

        return cuonSachMapper.toCuonSachResponse(cuonSach);
    }

    public List<CuonSachResponse> getCuonSachBySach(Integer maSach) {
        if (!sachRepository.existsById(maSach)) {
            throw new RuntimeException("Không tìm thấy sách");
        }

        return cuonSachRepository.findBySach_MaSach(maSach)
                .stream()
                .map(cuonSachMapper::toCuonSachResponse)
                .toList();
    }

    public CuonSachResponse updateCuonSach(Integer maCuonSach, CuonSachUpdateRequest request) {
        CuonSach cuonSach = cuonSachRepository.findById(maCuonSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuốn sách"));

        if (request.getMaSach() != null) {
            Sach sach = sachRepository.findById(request.getMaSach())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));
            cuonSach.setSach(sach);
        }

        if (request.getTinhTrang() != null) {
            cuonSach.setTinhTrang(request.getTinhTrang());
        }

        return cuonSachMapper.toCuonSachResponse(cuonSachRepository.save(cuonSach));
    }

    public void deleteCuonSach(Integer maCuonSach) {
        if (!cuonSachRepository.existsById(maCuonSach)) {
            throw new RuntimeException("Không tìm thấy cuốn sách");
        }

        cuonSachRepository.deleteById(maCuonSach);
    }
}