package com.lms.library.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TacGiaCreationRequest {

    String tenTacGia;
    LocalDate ngaySinh;
}