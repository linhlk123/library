package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SachResponse {

    Integer maSach;
    Integer maDauSach;
    String tenDauSach;
    Integer maTheLoai;
    String tenTheLoai;
    String nhaXuatBan;
    Integer namXuatBan;
    Integer soLuong;
    BigDecimal giaTien;
}