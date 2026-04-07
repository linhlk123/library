package com.lms.library.service;

import com.lms.library.dto.request.ThamSoCreationRequest;
import com.lms.library.dto.request.ThamSoUpdateRequest;
import com.lms.library.dto.response.ThamSoResponse;
import com.lms.library.entity.ThamSo;
import com.lms.library.mapper.ThamSoMapper;
import com.lms.library.repository.ThamSoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThamSoService {

    ThamSoRepository thamSoRepository;
    ThamSoMapper thamSoMapper;

    public ThamSoResponse createThamSo(ThamSoCreationRequest request) {
        if (thamSoRepository.existsById(request.getTenThamSo())) {
            throw new RuntimeException("Tham số đã tồn tại");
        }

        ThamSo thamSo = ThamSo.builder()
                .tenThamSo(request.getTenThamSo())
                .giaTri(request.getGiaTri())
                .build();

        return thamSoMapper.toThamSoResponse(thamSoRepository.save(thamSo));
    }

    public List<ThamSoResponse> getAllThamSo() {
        return thamSoRepository.findAll()
                .stream()
                .map(thamSoMapper::toThamSoResponse)
                .toList();
    }

    public ThamSoResponse getThamSoById(String tenThamSo) {
        ThamSo thamSo = thamSoRepository.findById(tenThamSo)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tham số"));

        return thamSoMapper.toThamSoResponse(thamSo);
    }

    public ThamSoResponse updateThamSo(String tenThamSo, ThamSoUpdateRequest request) {
        ThamSo thamSo = thamSoRepository.findById(tenThamSo)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tham số"));

        thamSo.setGiaTri(request.getGiaTri());

        return thamSoMapper.toThamSoResponse(thamSoRepository.save(thamSo));
    }

    public void deleteThamSo(String tenThamSo) {
        if (!thamSoRepository.existsById(tenThamSo)) {
            throw new RuntimeException("Không tìm thấy tham số");
        }

        thamSoRepository.deleteById(tenThamSo);
    }
}