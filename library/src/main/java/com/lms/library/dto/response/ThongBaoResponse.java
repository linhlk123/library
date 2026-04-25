package com.lms.library.dto.response;

import java.time.LocalDateTime;

import com.lms.library.enums.LoaiThongBao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Response khi trả về thông báo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongBaoResponse {

    Long id;
    String tieuDe;
    String noiDung;
    LoaiThongBao loaiThongBao;
    Boolean daDoc;
    LocalDateTime ngayTao;
    String nguoiNhan;
}
