package com.lms.library.service;

import com.lms.library.dto.request.DauSachCreationRequest;
import com.lms.library.dto.request.DauSachUpdateRequest;
import com.lms.library.dto.response.DauSachResponse;
import com.lms.library.entity.DauSach;
import com.lms.library.entity.TheLoai;
import com.lms.library.mapper.DauSachMapper;
import com.lms.library.repository.DauSachRepository;
import com.lms.library.repository.TheLoaiRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DauSachService {

    DauSachRepository dauSachRepository;
    TheLoaiRepository theLoaiRepository;
    DauSachMapper dauSachMapper;

    public DauSachResponse createDauSach(DauSachCreationRequest request) {
        TheLoai theLoai = theLoaiRepository.findById(request.getMaTheLoai())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));

        DauSach dauSach = DauSach.builder()
                .tenDauSach(request.getTenDauSach())
                .theLoai(theLoai)
                .build();

        return dauSachMapper.toDauSachResponse(dauSachRepository.save(dauSach));
    }

    public List<DauSachResponse> getAllDauSach() {
        return dauSachRepository.findAll()
                .stream()
                .map(dauSachMapper::toDauSachResponse)
                .toList();
    }

    public DauSachResponse getDauSachById(Integer maDauSach) {
        DauSach dauSach = dauSachRepository.findById(maDauSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        return dauSachMapper.toDauSachResponse(dauSach);
    }

    public List<DauSachResponse> getDauSachByTheLoai(Integer maTheLoai) {
        if (!theLoaiRepository.existsById(maTheLoai)) {
            throw new RuntimeException("Không tìm thấy thể loại");
        }

        return dauSachRepository.findByTheLoai_MaTheLoai(maTheLoai)
                .stream()
                .map(dauSachMapper::toDauSachResponse)
                .toList();
    }

    public DauSachResponse updateDauSach(Integer maDauSach, DauSachUpdateRequest request) {
        DauSach dauSach = dauSachRepository.findById(maDauSach)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đầu sách"));

        if (request.getTenDauSach() != null) {
            dauSach.setTenDauSach(request.getTenDauSach());
        }

        if (request.getMaTheLoai() != null) {
            TheLoai theLoai = theLoaiRepository.findById(request.getMaTheLoai())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));
            dauSach.setTheLoai(theLoai);
        }

        return dauSachMapper.toDauSachResponse(dauSachRepository.save(dauSach));
    }

    public void deleteDauSach(Integer maDauSach) {
        if (!dauSachRepository.existsById(maDauSach)) {
            throw new RuntimeException("Không tìm thấy đầu sách");
        }

        dauSachRepository.deleteById(maDauSach);
    }
}