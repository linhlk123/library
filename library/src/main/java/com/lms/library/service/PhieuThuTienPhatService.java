package com.lms.library.service;

import com.lms.library.dto.request.PhieuThuTienPhatCreationRequest;
import com.lms.library.dto.request.PhieuThuTienPhatUpdateRequest;
import com.lms.library.dto.response.PhieuThuTienPhatResponse;
import com.lms.library.entity.DocGia;
import com.lms.library.entity.NguoiDung;
import com.lms.library.entity.PhieuThuTienPhat;
import com.lms.library.repository.DocGiaRepository;
import com.lms.library.repository.NguoiDungRepository;
import com.lms.library.repository.PhieuThuTienPhatRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuThuTienPhatService {

    PhieuThuTienPhatRepository phieuThuTienPhatRepository;
    DocGiaRepository docGiaRepository;
    NguoiDungRepository nguoiDungRepository;
    DocGiaService docGiaService;

    @Transactional
    public PhieuThuTienPhatResponse create(PhieuThuTienPhatCreationRequest request) {
        DocGia docGia = docGiaRepository.findById(request.getMaDocGia())
                .orElseThrow(() -> new RuntimeException("Độc giả không tồn tại"));

        NguoiDung nhanVien = nguoiDungRepository.findById(request.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

        BigDecimal soTienThu = request.getSoTienThu() != null ? request.getSoTienThu() : BigDecimal.ZERO;
        BigDecimal conLai = request.getConLai() != null ? request.getConLai() : BigDecimal.ZERO;

        if (soTienThu.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Số tiền thu không được âm");
        }

        if (conLai.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Còn lại không được âm");
        }

        PhieuThuTienPhat phieuThuTienPhat = PhieuThuTienPhat.builder()
                .docGia(docGia)
                .ngayThu(request.getNgayThu())
                .soTienThu(soTienThu)
                .conLai(conLai)
                .nhanVien(nhanVien)
                .build();

        phieuThuTienPhatRepository.save(phieuThuTienPhat);

        // Cập nhật tongNo của độc giả nếu soTienThu > 0
        if (soTienThu.compareTo(BigDecimal.ZERO) > 0) {
            docGiaService.updateTongNoDocGia(docGia.getMaDocGia());
        }

        return toResponse(phieuThuTienPhat);
    }

    public List<PhieuThuTienPhatResponse> getAll() {
        return phieuThuTienPhatRepository.findAllWithDetails()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PhieuThuTienPhatResponse getById(Integer soPTT) {
        PhieuThuTienPhat phieuThuTienPhat = phieuThuTienPhatRepository.findByIdWithDetails(soPTT)
                .orElseThrow(() -> new RuntimeException("Fine collection receipt does not exist"));

        return toResponse(phieuThuTienPhat);
    }

    @Transactional
    public PhieuThuTienPhatResponse update(Integer soPTT, PhieuThuTienPhatUpdateRequest request) {
        PhieuThuTienPhat phieuThuTienPhat = phieuThuTienPhatRepository.findById(soPTT)
                .orElseThrow(() -> new RuntimeException("Fine collection receipt does not exist"));

        DocGia docGia = docGiaRepository.findById(request.getMaDocGia())
                .orElseThrow(() -> new RuntimeException("Reader does not exist"));

        NguoiDung nhanVien = nguoiDungRepository.findById(request.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Employee does not exist"));

        BigDecimal soTienThu = request.getSoTienThu() != null ? request.getSoTienThu() : BigDecimal.ZERO;
        BigDecimal conLai = request.getConLai() != null ? request.getConLai() : BigDecimal.ZERO;

        if (soTienThu.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Collection amount cannot be negative");
        }

        if (conLai.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Remaining amount cannot be negative");
        }

        phieuThuTienPhat.setDocGia(docGia);
        phieuThuTienPhat.setNgayThu(request.getNgayThu());
        phieuThuTienPhat.setSoTienThu(soTienThu);
        phieuThuTienPhat.setConLai(conLai);
        phieuThuTienPhat.setNhanVien(nhanVien);

        phieuThuTienPhatRepository.save(phieuThuTienPhat);

        // Cập nhật tongNo của độc giả nếu soTienThu > 0
        if (soTienThu.compareTo(BigDecimal.ZERO) > 0) {
            docGiaService.updateTongNoDocGia(docGia.getMaDocGia());
        }

        return toResponse(phieuThuTienPhat);
    }

    public void delete(Integer soPTT) {
        PhieuThuTienPhat phieuThuTienPhat = phieuThuTienPhatRepository.findById(soPTT)
                .orElseThrow(() -> new RuntimeException("Fine collection receipt does not exist"));

        phieuThuTienPhatRepository.delete(phieuThuTienPhat);
    }

    private PhieuThuTienPhatResponse toResponse(PhieuThuTienPhat entity) {
        return PhieuThuTienPhatResponse.builder()
                .soPTT(entity.getSoPTT())
                .maDocGia(entity.getDocGia() != null ? entity.getDocGia().getMaDocGia() : null)
                .tenDocGia(entity.getDocGia() != null ? entity.getDocGia().getNguoiDung().getHoTen() : null)
                .ngayThu(entity.getNgayThu())
                .soTienThu(entity.getSoTienThu())
                .conLai(entity.getConLai())
                .maNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getTenDangNhap() : null)
                .tenNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getHoTen() : null)
                .build();
    }
}