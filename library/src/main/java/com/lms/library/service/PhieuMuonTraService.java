package com.lms.library.service;

import com.lms.library.dto.request.PhieuMuonTraCreationRequest;
import com.lms.library.dto.request.PhieuMuonTraUpdateRequest;
import com.lms.library.dto.response.PhieuMuonTraResponse;
import com.lms.library.entity.CuonSach;
import com.lms.library.entity.DocGia;
import com.lms.library.entity.NguoiDung;
import com.lms.library.entity.PhieuMuonTra;
import com.lms.library.repository.CuonSachRepository;
import com.lms.library.repository.DocGiaRepository;
import com.lms.library.repository.NguoiDungRepository;
import com.lms.library.repository.PhieuMuonTraRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhieuMuonTraService {

    PhieuMuonTraRepository phieuMuonTraRepository;
    CuonSachRepository cuonSachRepository;
    DocGiaRepository docGiaRepository;
    NguoiDungRepository nguoiDungRepository;

    public PhieuMuonTraResponse create(PhieuMuonTraCreationRequest request) {
        CuonSach cuonSach = cuonSachRepository.findById(request.getMaCuonSach())
                .orElseThrow(() -> new RuntimeException("Cuốn sách không tồn tại"));

        DocGia docGia = docGiaRepository.findById(request.getMaDocGia())
                .orElseThrow(() -> new RuntimeException("Độc giả không tồn tại"));

        NguoiDung nhanVien = nguoiDungRepository.findById(request.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

        validateRequest(request);

        Integer soNgayMuon = tinhSoNgayMuon(request.getNgayMuon(), request.getNgayTra(), request.getSoNgayMuon());
        BigDecimal tienPhat = request.getTienPhat() != null ? request.getTienPhat() : BigDecimal.ZERO;

        PhieuMuonTra phieuMuonTra = PhieuMuonTra.builder()
                .cuonSach(cuonSach)
                .docGia(docGia)
                .ngayMuon(request.getNgayMuon())
                .ngayPhaiTra(request.getNgayPhaiTra())
                .ngayTra(request.getNgayTra())
                .soNgayMuon(soNgayMuon)
                .tienPhat(tienPhat)
                .nhanVien(nhanVien)
                .build();

        return toResponse(phieuMuonTraRepository.save(phieuMuonTra));
    }

    public List<PhieuMuonTraResponse> getAll() {
        return phieuMuonTraRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PhieuMuonTraResponse getById(Integer soPhieu) {
        PhieuMuonTra phieuMuonTra = phieuMuonTraRepository.findById(soPhieu)
                .orElseThrow(() -> new RuntimeException("Phiếu mượn trả không tồn tại"));

        return toResponse(phieuMuonTra);
    }

    public PhieuMuonTraResponse update(Integer soPhieu, PhieuMuonTraUpdateRequest request) {
        PhieuMuonTra phieuMuonTra = phieuMuonTraRepository.findById(soPhieu)
                .orElseThrow(() -> new RuntimeException("Phiếu mượn trả không tồn tại"));

        CuonSach cuonSach = cuonSachRepository.findById(request.getMaCuonSach())
                .orElseThrow(() -> new RuntimeException("Cuốn sách không tồn tại"));

        DocGia docGia = docGiaRepository.findById(request.getMaDocGia())
                .orElseThrow(() -> new RuntimeException("Độc giả không tồn tại"));

        NguoiDung nhanVien = nguoiDungRepository.findById(request.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

        validateRequest(request);

        Integer soNgayMuon = tinhSoNgayMuon(request.getNgayMuon(), request.getNgayTra(), request.getSoNgayMuon());
        BigDecimal tienPhat = request.getTienPhat() != null ? request.getTienPhat() : BigDecimal.ZERO;

        phieuMuonTra.setCuonSach(cuonSach);
        phieuMuonTra.setDocGia(docGia);
        phieuMuonTra.setNgayMuon(request.getNgayMuon());
        phieuMuonTra.setNgayPhaiTra(request.getNgayPhaiTra());
        phieuMuonTra.setNgayTra(request.getNgayTra());
        phieuMuonTra.setSoNgayMuon(soNgayMuon);
        phieuMuonTra.setTienPhat(tienPhat);
        phieuMuonTra.setNhanVien(nhanVien);

        return toResponse(phieuMuonTraRepository.save(phieuMuonTra));
    }

    public void delete(Integer soPhieu) {
        PhieuMuonTra phieuMuonTra = phieuMuonTraRepository.findById(soPhieu)
                .orElseThrow(() -> new RuntimeException("Phiếu mượn trả không tồn tại"));

        phieuMuonTraRepository.delete(phieuMuonTra);
    }

    private void validateRequest(PhieuMuonTraCreationRequest request) {
        if (request.getNgayMuon() == null) {
            throw new RuntimeException("Ngày mượn không được để trống");
        }

        if (request.getNgayPhaiTra() != null && request.getNgayPhaiTra().isBefore(request.getNgayMuon())) {
            throw new RuntimeException("Ngày phải trả không được nhỏ hơn ngày mượn");
        }

        if (request.getNgayTra() != null && request.getNgayTra().isBefore(request.getNgayMuon())) {
            throw new RuntimeException("Ngày trả không được nhỏ hơn ngày mượn");
        }

        if (request.getTienPhat() != null && request.getTienPhat().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Tiền phạt không được âm");
        }
    }

    private void validateRequest(PhieuMuonTraUpdateRequest request) {
        if (request.getNgayMuon() == null) {
            throw new RuntimeException("Ngày mượn không được để trống");
        }

        if (request.getNgayPhaiTra() != null && request.getNgayPhaiTra().isBefore(request.getNgayMuon())) {
            throw new RuntimeException("Ngày phải trả không được nhỏ hơn ngày mượn");
        }

        if (request.getNgayTra() != null && request.getNgayTra().isBefore(request.getNgayMuon())) {
            throw new RuntimeException("Ngày trả không được nhỏ hơn ngày mượn");
        }

        if (request.getTienPhat() != null && request.getTienPhat().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Tiền phạt không được âm");
        }
    }

    private Integer tinhSoNgayMuon(java.time.LocalDate ngayMuon, java.time.LocalDate ngayTra, Integer soNgayMuonInput) {
        if (ngayMuon != null && ngayTra != null) {
            return (int) ChronoUnit.DAYS.between(ngayMuon, ngayTra);
        }
        return soNgayMuonInput != null ? soNgayMuonInput : 0;
    }

    private PhieuMuonTraResponse toResponse(PhieuMuonTra entity) {
        return PhieuMuonTraResponse.builder()
                .soPhieu(entity.getSoPhieu())
                .maCuonSach(entity.getCuonSach() != null ? entity.getCuonSach().getMaCuonSach() : null)
                // .tenCuonSach(entity.getCuonSach() != null ?
                // entity.getCuonSach().getTenCuonSach() : null)
                .maDocGia(entity.getDocGia() != null ? entity.getDocGia().getMaDocGia() : null)
                .tenDocGia(entity.getDocGia() != null ? entity.getDocGia().getHoTen() : null)
                .ngayMuon(entity.getNgayMuon())
                .ngayPhaiTra(entity.getNgayPhaiTra())
                .ngayTra(entity.getNgayTra())
                .soNgayMuon(entity.getSoNgayMuon())
                .tienPhat(entity.getTienPhat())
                .maNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getTenDangNhap() : null)
                .tenNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getHoTen() : null)
                .build();
    }
}