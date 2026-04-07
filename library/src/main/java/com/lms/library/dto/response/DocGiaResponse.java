package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocGiaResponse {

    Integer maDocGia;
    Integer maLoaiDocGia;
    String tenLoaiDocGia;

    String hoTen;
    LocalDate ngaySinh;
    String diaChi;
    String email;

    LocalDate ngayLapThe;
    LocalDate ngayHetHan;
    BigDecimal tongNo;

    String tenVaiTro;
}