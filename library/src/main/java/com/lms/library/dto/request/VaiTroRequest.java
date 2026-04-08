package com.lms.library.dto.request;

import java.util.Set;

import com.lms.library.dto.request.PhanQuyenRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.Data;
import lombok.AccessLevel;

@Data
@NoArgsConstructor  
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VaiTroRequest {
    String tenVaiTro;
    String moTaVaiTro;
    Set<PhanQuyenRequest> phanQuyen;
}
