package com.lms.library.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuMuonTraCreationRequest {

    Integer maCuonSach;
    String maDocGia;
    LocalDate ngayMuon;
    LocalDate ngayPhaiTra;
    LocalDate ngayTra;
    Integer soNgayMuon;
    BigDecimal tienPhat;
    String maNhanVien; // TenDangNhap
    String trangThai; // PENDING, ACTIVE, REJECTED, RETURNED
}