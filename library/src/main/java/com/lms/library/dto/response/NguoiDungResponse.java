package com.lms.library.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NguoiDungResponse {
    String tenDangNhap;
    String hoTen;
    LocalDate ngaySinh;
    String diaChi;
    String email;
    String vaiTro; // Role name from VaiTro.tenVaiTro
}
