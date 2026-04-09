package com.lms.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuMuonTraUpdateRequest {

    Integer maCuonSach;
    Integer maDocGia;
    LocalDate ngayMuon;
    LocalDate ngayPhaiTra;
    LocalDate ngayTra;
    Integer soNgayMuon;
    BigDecimal tienPhat;
    String maNhanVien; // TenDangNhap
}