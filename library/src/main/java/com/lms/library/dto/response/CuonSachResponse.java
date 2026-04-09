package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CuonSachResponse {

    Integer maCuonSach;
    Integer maSach;
    Integer maDauSach;
    String tenDauSach;
    String nhaXuatBan;
    Integer namXuatBan;
    String tinhTrang;
}