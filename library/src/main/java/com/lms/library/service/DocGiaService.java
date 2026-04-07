package com.lms.library.service;

import com.lms.library.dto.request.DocGiaCreationRequest;
import com.lms.library.dto.request.DocGiaUpdateRequest;
import com.lms.library.dto.response.DocGiaResponse;
import com.lms.library.entity.DocGia;
import com.lms.library.entity.LoaiDocGia;
import com.lms.library.entity.Role;
import com.lms.library.mapper.DocGiaMapper;
import com.lms.library.repository.DocGiaRepository;
import com.lms.library.repository.LoaiDocGiaRepository;
import com.lms.library.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocGiaService {

    DocGiaRepository docGiaRepository;
    LoaiDocGiaRepository loaiDocGiaRepository;
    RoleRepository RoleRepository;
    DocGiaMapper docGiaMapper;

    public DocGiaResponse createDocGia(DocGiaCreationRequest request) {
        if (request.getEmail() != null && docGiaRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(request.getMaLoaiDocGia())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại độc giả"));

        Role role = RoleRepository.findById("DOCGIA")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò DOCGIA"));

        DocGia docGia = DocGia.builder()
                .loaiDocGia(loaiDocGia)
                .hoTen(request.getHoTen())
                .ngaySinh(request.getNgaySinh())
                .diaChi(request.getDiaChi())
                .email(request.getEmail())
                .matKhau(request.getMatKhau())
                .ngayLapThe(request.getNgayLapThe())
                .ngayHetHan(request.getNgayHetHan())
                .tongNo(BigDecimal.ZERO)
                .role(role)
                .build();

        return docGiaMapper.toDocGiaResponse(docGiaRepository.save(docGia));
    }

    public List<DocGiaResponse> getAllDocGia() {
        return docGiaRepository.findAll()
                .stream()
                .map(docGiaMapper::toDocGiaResponse)
                .toList();
    }

    public DocGiaResponse getDocGiaById(Integer maDocGia) {
        DocGia docGia = docGiaRepository.findById(maDocGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy độc giả"));

        return docGiaMapper.toDocGiaResponse(docGia);
    }

    public DocGiaResponse updateDocGia(Integer maDocGia, DocGiaUpdateRequest request) {
        DocGia docGia = docGiaRepository.findById(maDocGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy độc giả"));

        LoaiDocGia loaiDocGia = loaiDocGiaRepository.findById(request.getMaLoaiDocGia())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại độc giả"));

        docGia.setLoaiDocGia(loaiDocGia);
        docGia.setHoTen(request.getHoTen());
        docGia.setNgaySinh(request.getNgaySinh());
        docGia.setDiaChi(request.getDiaChi());
        docGia.setEmail(request.getEmail());
        docGia.setMatKhau(request.getMatKhau());
        docGia.setNgayLapThe(request.getNgayLapThe());
        docGia.setNgayHetHan(request.getNgayHetHan());

        return docGiaMapper.toDocGiaResponse(docGiaRepository.save(docGia));
    }

    public void deleteDocGia(Integer maDocGia) {
        if (!docGiaRepository.existsById(maDocGia)) {
            throw new RuntimeException("Không tìm thấy độc giả");
        }
        docGiaRepository.deleteById(maDocGia);
    }
}