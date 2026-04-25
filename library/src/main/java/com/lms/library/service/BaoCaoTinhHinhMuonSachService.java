package com.lms.library.service;

import com.lms.library.dto.request.BaoCaoTinhHinhMuonSachCreationRequest;
import com.lms.library.dto.request.BaoCaoTinhHinhMuonSachUpdateRequest;
import com.lms.library.dto.response.BaoCaoTinhHinhMuonSachResponse;
import com.lms.library.entity.BaoCaoTinhHinhMuonSach;
import com.lms.library.mapper.BaoCaoTinhHinhMuonSachMapper;
import com.lms.library.repository.BaoCaoTinhHinhMuonSachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaoCaoTinhHinhMuonSachService {

    BaoCaoTinhHinhMuonSachRepository repository;
    BaoCaoTinhHinhMuonSachMapper mapper;

    public BaoCaoTinhHinhMuonSachResponse create(BaoCaoTinhHinhMuonSachCreationRequest request) {
        BaoCaoTinhHinhMuonSach baoCao = BaoCaoTinhHinhMuonSach.builder()
                .thang(request.getThang())
                .nam(request.getNam())
                .tongSoLuotMuon(
                        request.getTongSoLuotMuon() != null ? request.getTongSoLuotMuon() : 0)
                .build();

        return mapper.toResponse(repository.save(baoCao));
    }

    public List<BaoCaoTinhHinhMuonSachResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public BaoCaoTinhHinhMuonSachResponse getById(Integer maBCTHMS) {
        BaoCaoTinhHinhMuonSach baoCao = repository.findById(maBCTHMS)
                .orElseThrow(() -> new RuntimeException("Borrowing report not found"));

        return mapper.toResponse(baoCao);
    }

    public BaoCaoTinhHinhMuonSachResponse update(Integer maBCTHMS, BaoCaoTinhHinhMuonSachUpdateRequest request) {
        BaoCaoTinhHinhMuonSach baoCao = repository.findById(maBCTHMS)
                .orElseThrow(() -> new RuntimeException("Borrowing report not found"));

        baoCao.setThang(request.getThang());
        baoCao.setNam(request.getNam());
        baoCao.setTongSoLuotMuon(request.getTongSoLuotMuon());

        return mapper.toResponse(repository.save(baoCao));
    }

    public void delete(Integer maBCTHMS) {
        if (!repository.existsById(maBCTHMS)) {
            throw new RuntimeException("Borrowing report not found");
        }

        repository.deleteById(maBCTHMS);
    }
}