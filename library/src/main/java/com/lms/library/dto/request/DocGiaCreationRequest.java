package com.lms.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocGiaCreationRequest {

    Integer maLoaiDocGia;
    String hoTen;
    LocalDate ngaySinh;
    String diaChi;
    String email;
    String matKhau;
    LocalDate ngayLapThe;
    LocalDate ngayHetHan;
}