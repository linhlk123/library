package com.lms.library.dto.response;

import java.util.Set;

import com.lms.library.dto.response.PhanQuyenResponse;
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
public class VaiTroResponse {
    String tenVaiTro;
    String moTaVaiTro;
    Set<PhanQuyenResponse> danhSachPhanQuyen;
}
