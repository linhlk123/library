package com.lms.library.service;

import com.lms.library.dto.request.CTBC_THMSCreationRequest;
import com.lms.library.dto.request.CTBC_THMSUpdateRequest;
import com.lms.library.dto.response.CTBC_THMSResponse;
import com.lms.library.entity.*;
import com.lms.library.mapper.CTBC_THMSMapper;
import com.lms.library.repository.BaoCaoTinhHinhMuonSachRepository;
import com.lms.library.repository.CTBC_THMSRepository;
import com.lms.library.repository.TheLoaiRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CTBC_THMSService {

    CTBC_THMSRepository repository;
    BaoCaoTinhHinhMuonSachRepository baoCaoRepository;
    TheLoaiRepository theLoaiRepository;
    CTBC_THMSMapper mapper;

    public CTBC_THMSResponse create(CTBC_THMSCreationRequest request) {
        BaoCaoTinhHinhMuonSach baoCao = baoCaoRepository.findById(request.getMaBCTHMS())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo tình hình mượn sách"));

        TheLoai theLoai = theLoaiRepository.findById(request.getMaTheLoai())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));

        CTBC_THMSId id = new CTBC_THMSId(request.getMaBCTHMS(), request.getMaTheLoai());

        if (repository.existsById(id)) {
            throw new RuntimeException("Chi tiết báo cáo này đã tồn tại");
        }

        CTBC_THMS chiTiet = CTBC_THMS.builder()
                .baoCaoTinhHinhMuonSach(baoCao)
                .theLoai(theLoai)
                .soLuotMuon(request.getSoLuotMuon() != null ? request.getSoLuotMuon() : 0)
                .tiLe(request.getTiLe() != null ? request.getTiLe() : BigDecimal.ZERO)
                .build();

        return mapper.toResponse(repository.save(chiTiet));
    }

    public List<CTBC_THMSResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<CTBC_THMSResponse> getByBaoCao(Integer maBCTHMS) {
        return repository.findByBaoCaoTinhHinhMuonSach_MaBCTHMS(maBCTHMS)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CTBC_THMSResponse getById(Integer maBCTHMS, Integer maTheLoai) {
        CTBC_THMSId id = new CTBC_THMSId(maBCTHMS, maTheLoai);

        CTBC_THMS chiTiet = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết báo cáo"));

        return mapper.toResponse(chiTiet);
    }

    public CTBC_THMSResponse update(Integer maBCTHMS, Integer maTheLoai, CTBC_THMSUpdateRequest request) {
        CTBC_THMSId id = new CTBC_THMSId(maBCTHMS, maTheLoai);

        CTBC_THMS chiTiet = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết báo cáo"));

        chiTiet.setSoLuotMuon(request.getSoLuotMuon());
        chiTiet.setTiLe(request.getTiLe());

        return mapper.toResponse(repository.save(chiTiet));
    }

    public void delete(Integer maBCTHMS, Integer maTheLoai) {
        CTBC_THMSId id = new CTBC_THMSId(maBCTHMS, maTheLoai);

        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy chi tiết báo cáo");
        }

        repository.deleteById(id);
    }
}