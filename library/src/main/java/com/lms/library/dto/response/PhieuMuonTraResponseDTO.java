package com.lms.library.dto.response;

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
public class PhieuMuonTraResponseDTO {

    Integer soPhieu;
    LocalDate ngayMuon;
    LocalDate ngayPhaiTra;
    LocalDate ngayTra;
    BigDecimal tienPhat;
    String trangThai;
    String maDocGia;

    Integer maCuonSach;
    String tenDauSach;
    String anhBiaUrl;
    String tacGia;
    String nhaXuatBan;
}