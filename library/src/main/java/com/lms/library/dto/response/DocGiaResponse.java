package com.lms.library.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocGiaResponse {

    String maDocGia; // Username (tenDangNhap) from NGUOIDUNG
    Integer maLoaiDocGia; // Reference to LOAIDOCGIA
    String tenLoaiDocGia; // Card type name

    String hoTen; // Full name from NGUOIDUNG
    String email; // Email from NGUOIDUNG
    String diaChi; // Address from NGUOIDUNG
    String matKhau; // Password from NGUOIDUNG (masked in real APIs)
    String tenVaiTro; // Role name from NGUOIDUNG.vaiTro

    LocalDate ngayLapThe; // Card issuance date
    LocalDate ngayHetHan; // Card expiration date
    BigDecimal tongNo; // Fine amount
}
