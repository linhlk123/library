package com.lms.library.dto.request;

import com.lms.library.enums.LoaiThongBao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Request để tạo thông báo mới
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongBaoCreationRequest {

    String tieuDe;
    String noiDung;
    LoaiThongBao loaiThongBao;
    String nguoiNhan; 
}
