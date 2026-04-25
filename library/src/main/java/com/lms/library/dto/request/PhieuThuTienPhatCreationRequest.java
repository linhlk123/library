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
public class PhieuThuTienPhatCreationRequest {

    String maDocGia;
    LocalDate ngayThu;
    BigDecimal soTienThu;
    BigDecimal conLai;
    String maNhanVien; // chính là TenDangNhap
}