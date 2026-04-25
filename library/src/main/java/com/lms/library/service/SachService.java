package com.lms.library.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lms.library.dto.request.SachCreationRequest;
import com.lms.library.dto.request.SachUpdateRequest;
import com.lms.library.dto.response.PageResponseDTO;
import com.lms.library.dto.response.SachResponse;
import com.lms.library.entity.DauSach;
import com.lms.library.entity.Sach;
import com.lms.library.mapper.SachMapper;
import com.lms.library.repository.DauSachRepository;
import com.lms.library.repository.SachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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

        Integer soLuong = request.getSoLuong();
        if (soLuong == null) {
            soLuong = 0;
        }

        Sach sach = Sach.builder()
                .dauSach(dauSach)
                .nhaXuatBan(request.getNhaXuatBan())
                .namXuatBan(request.getNamXuatBan())
                .soLuong(soLuong)
                .giaTien(request.getGiaTien())
                .build();

        return sachMapper.toSachResponse(sachRepository.save(sach));
    }

    public PageResponseDTO<SachResponse> getAllSach(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        // Lấy Page<Sach> từ Spring Data JPA để không kéo toàn bộ dữ liệu ra một lần.
        // Sau đó map từng phần tử của Page sang DTO để Frontend chỉ nhận đúng dữ liệu cần hiển thị.
        Page<Sach> sachPage = sachRepository.findAll(pageRequest);
        Page<SachResponse> responsePage = sachPage.map(sachMapper::toSachResponse);

        return PageResponseDTO.<SachResponse>builder()
                .content(responsePage.getContent())
                .currentPage(responsePage.getNumber() + 1)
                .totalPages(responsePage.getTotalPages())
                .totalElements(responsePage.getTotalElements())
                .pageSize(responsePage.getSize())
                .build();
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