package com.lms.library.service;

import com.lms.library.dto.request.TacGiaCreationRequest;
import com.lms.library.dto.request.TacGiaUpdateRequest;
import com.lms.library.dto.response.TacGiaResponse;
import com.lms.library.entity.TacGia;
import com.lms.library.mapper.TacGiaMapper;
import com.lms.library.repository.TacGiaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TacGiaService {

    TacGiaRepository tacGiaRepository;
    TacGiaMapper tacGiaMapper;

    public TacGiaResponse createTacGia(TacGiaCreationRequest request) {
        TacGia tacGia = TacGia.builder()
                .tenTacGia(request.getTenTacGia())
                .ngaySinh(request.getNgaySinh())
                .build();

        return tacGiaMapper.toTacGiaResponse(tacGiaRepository.save(tacGia));
    }

    public List<TacGiaResponse> getAllTacGia() {
        return tacGiaRepository.findAll()
                .stream()
                .map(tacGiaMapper::toTacGiaResponse)
                .toList();
    }

    public TacGiaResponse getTacGiaById(Integer maTacGia) {
        TacGia tacGia = tacGiaRepository.findById(maTacGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả"));

        return tacGiaMapper.toTacGiaResponse(tacGia);
    }

    public TacGiaResponse updateTacGia(Integer maTacGia, TacGiaUpdateRequest request) {
        TacGia tacGia = tacGiaRepository.findById(maTacGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả"));

        if (request.getTenTacGia() != null) {
            tacGia.setTenTacGia(request.getTenTacGia());
        }

        if (request.getNgaySinh() != null) {
            tacGia.setNgaySinh(request.getNgaySinh());
        }

        return tacGiaMapper.toTacGiaResponse(tacGiaRepository.save(tacGia));
    }

    public void deleteTacGia(Integer maTacGia) {
        if (!tacGiaRepository.existsById(maTacGia)) {
            throw new RuntimeException("Không tìm thấy tác giả");
        }

        tacGiaRepository.deleteById(maTacGia);
    }
}