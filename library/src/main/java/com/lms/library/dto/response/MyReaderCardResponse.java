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
public class MyReaderCardResponse {

    String maDocGia; // Username (tenDangNhap)
    String tenDocGia; // Full name from NGUOIDUNG
    String tenLoaiDocGia; // Card type name
    LocalDate ngayLapThe; // Card issuance date
    LocalDate ngayHetHan; // Card expiration date
    BigDecimal tongNo; // Outstanding fine amount
    Boolean cardValid; // Is card still valid (ngayHetHan >= today)
    String cardStatus; // Status: VALID, EXPIRED, PENDING_FEES
}
