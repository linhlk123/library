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
public class PhieuThuTienPhatResponse {

    Integer soPTT;
    String maDocGia;
    String tenDocGia;
    LocalDate ngayThu;
    BigDecimal soTienThu;
    BigDecimal conLai;
    String maNhanVien;
    String tenNhanVien;
}