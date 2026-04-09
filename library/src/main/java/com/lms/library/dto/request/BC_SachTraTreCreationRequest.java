package com.lms.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BC_SachTraTreCreationRequest {

    LocalDate ngay;
    Integer maCuonSach;
    LocalDate ngayMuon;
    Integer soNgayTraTre;
}