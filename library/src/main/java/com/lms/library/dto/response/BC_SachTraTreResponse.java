package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BC_SachTraTreResponse {

    LocalDate ngay;
    Integer maCuonSach;
    Integer maSach;
    String tenDauSach;
    LocalDate ngayMuon;
    Integer soNgayTraTre;
}