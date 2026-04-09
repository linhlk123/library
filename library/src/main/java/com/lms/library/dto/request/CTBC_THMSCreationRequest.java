package com.lms.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CTBC_THMSCreationRequest {

    Integer maBCTHMS;
    Integer maTheLoai;
    Integer soLuotMuon;
    BigDecimal tiLe;
}