package com.lms.library.dto.request;

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
public class BaoCaoTinhHinhMuonSachCreationRequest {
    Integer thang;
    Integer nam;
    Integer tongSoLuotMuon;
}