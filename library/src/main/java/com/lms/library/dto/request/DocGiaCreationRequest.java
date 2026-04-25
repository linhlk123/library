package com.lms.library.dto.request;

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
public class DocGiaCreationRequest {

    String maDocGia; // Username (tenDangNhap) - must exist in NGUOIDUNG table
    Integer maLoaiDocGia; // Reference to LOAIDOCGIA
    LocalDate ngayLapThe; // Card issuance date
    LocalDate ngayHetHan; // Card expiration date
}