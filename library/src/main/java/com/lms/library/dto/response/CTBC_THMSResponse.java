package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CTBC_THMSResponse {

    Integer maBCTHMS;
    Integer maTheLoai;
    String tenTheLoai;
    Integer soLuotMuon;
    BigDecimal tiLe;
}