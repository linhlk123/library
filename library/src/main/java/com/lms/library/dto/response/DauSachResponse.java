package com.lms.library.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DauSachResponse {

    Integer maDauSach;
    String tenDauSach;
    TheLoaiResponse theLoai;
    String anhBiaUrl;
    List<TacGiaResponse> tacGiaList;
}