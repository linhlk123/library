package com.lms.library.service;

import com.lms.library.dto.request.CT_TacGiaCreationRequest;
import com.lms.library.dto.response.CT_TacGiaResponse;
import com.lms.library.entity.CT_TacGia;
import com.lms.library.entity.CT_TacGiaId;
import com.lms.library.entity.DauSach;
import com.lms.library.entity.TacGia;
import com.lms.library.mapper.CT_TacGiaMapper;
import com.lms.library.repository.CT_TacGiaRepository;
import com.lms.library.repository.DauSachRepository;
import com.lms.library.repository.TacGiaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CT_TacGiaService {

    CT_TacGiaRepository ctTacGiaRepository;
    DauSachRepository dauSachRepository;
    TacGiaRepository tacGiaRepository;
    CT_TacGiaMapper ctTacGiaMapper;

    public CT_TacGiaResponse createCT_TacGia(CT_TacGiaCreationRequest request) {
        if (ctTacGiaRepository.existsByDauSach_MaDauSachAndTacGia_MaTacGia(
                request.getMaDauSach(), request.getMaTacGia())) {
            throw new RuntimeException("Tác giả đã được gán cho đầu sách này");
        }

        DauSach dauSach = dauSachRepository.findById(request.getMaDauSach())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        TacGia tacGia = tacGiaRepository.findById(request.getMaTacGia())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả"));

        CT_TacGia ctTacGia = CT_TacGia.builder()
                .dauSach(dauSach)
                .tacGia(tacGia)
                .build();

        return ctTacGiaMapper.toCT_TacGiaResponse(ctTacGiaRepository.save(ctTacGia));
    }

    public List<CT_TacGiaResponse> getAllCT_TacGia() {
        return ctTacGiaRepository.findAll()
                .stream()
                .map(ctTacGiaMapper::toCT_TacGiaResponse)
                .toList();
    }

    public List<CT_TacGiaResponse> getByDauSach(Integer maDauSach) {
        if (!dauSachRepository.existsById(maDauSach)) {
            throw new RuntimeException("Không tìm thấy đầu sách");
        }

        return ctTacGiaRepository.findByDauSach_MaDauSach(maDauSach)
                .stream()
                .map(ctTacGiaMapper::toCT_TacGiaResponse)
                .toList();
    }

    public List<CT_TacGiaResponse> getByTacGia(Integer maTacGia) {
        if (!tacGiaRepository.existsById(maTacGia)) {
            throw new RuntimeException("Không tìm thấy tác giả");
        }

        return ctTacGiaRepository.findByTacGia_MaTacGia(maTacGia)
                .stream()
                .map(ctTacGiaMapper::toCT_TacGiaResponse)
                .toList();
    }

    public void deleteCtTacGia(Integer maDauSach, Integer maTacGia) {
        if (!ctTacGiaRepository.existsByDauSach_MaDauSachAndTacGia_MaTacGia(maDauSach, maTacGia)) {
            throw new RuntimeException("Không tìm thấy thông tin tác giả - đầu sách");
        }

        CT_TacGiaId id = new CT_TacGiaId(maDauSach, maTacGia);
        ctTacGiaRepository.deleteById(id);
    }
}