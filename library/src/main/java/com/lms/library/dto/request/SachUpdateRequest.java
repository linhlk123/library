package com.lms.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SachUpdateRequest {

    Integer maDauSach;
    String nhaXuatBan;
    Integer namXuatBan;
    Integer soLuong;
    BigDecimal giaTien;
}