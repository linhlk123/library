package com.lms.library.dto.response;

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
public class PhieuMuonTraResponse {

    Integer soPhieu;

    Integer maCuonSach;
    // String tenCuonSach;

    Integer maDocGia;
    String tenDocGia;

    LocalDate ngayMuon;
    LocalDate ngayPhaiTra;
    LocalDate ngayTra;
    Integer soNgayMuon;
    BigDecimal tienPhat;

    String maNhanVien;
    String tenNhanVien;
}