package com.lms.library.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TheLoaiResponse {

    Integer maTheLoai;
    String tenTheLoai;
}