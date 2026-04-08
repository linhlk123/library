package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TacGiaResponse {

    Integer maTacGia;
    String tenTacGia;
    LocalDate ngaySinh;
}