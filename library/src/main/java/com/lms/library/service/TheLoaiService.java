package com.lms.library.service;

import com.lms.library.dto.request.TheLoaiCreationRequest;
import com.lms.library.dto.request.TheLoaiUpdateRequest;
import com.lms.library.dto.response.TheLoaiResponse;
import com.lms.library.entity.TheLoai;
import com.lms.library.mapper.TheLoaiMapper;
import com.lms.library.repository.TheLoaiRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TheLoaiService {

    TheLoaiRepository theLoaiRepository;
    TheLoaiMapper theLoaiMapper;

    public TheLoaiResponse createTheLoai(TheLoaiCreationRequest request) {
        if (theLoaiRepository.existsByTenTheLoaiIgnoreCase(request.getTenTheLoai())) {
            throw new RuntimeException("Thể loại đã tồn tại");
        }

        TheLoai theLoai = TheLoai.builder()
                .tenTheLoai(request.getTenTheLoai())
                .build();

        return theLoaiMapper.toTheLoaiResponse(theLoaiRepository.save(theLoai));
    }

    public List<TheLoaiResponse> getAllTheLoai() {
        return theLoaiRepository.findAll()
                .stream()
                .map(theLoaiMapper::toTheLoaiResponse)
                .toList();
    }

    public TheLoaiResponse getTheLoaiById(Integer maTheLoai) {
        TheLoai theLoai = theLoaiRepository.findById(maTheLoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));

        return theLoaiMapper.toTheLoaiResponse(theLoai);
    }

    public TheLoaiResponse updateTheLoai(Integer maTheLoai, TheLoaiUpdateRequest request) {
        TheLoai theLoai = theLoaiRepository.findById(maTheLoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại"));

        if (theLoaiRepository.existsByTenTheLoaiIgnoreCase(request.getTenTheLoai())) {
            throw new RuntimeException("Tên thể loại đã tồn tại");
        }

        theLoai.setTenTheLoai(request.getTenTheLoai());

        return theLoaiMapper.toTheLoaiResponse(theLoaiRepository.save(theLoai));
    }

    public void deleteTheLoai(Integer maTheLoai) {
        if (!theLoaiRepository.existsById(maTheLoai)) {
            throw new RuntimeException("Không tìm thấy thể loại");
        }

        theLoaiRepository.deleteById(maTheLoai);
    }
}