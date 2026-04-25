package com.lms.library.mapper;

import org.springframework.stereotype.Component;

import com.lms.library.dto.response.ThongBaoResponse;
import com.lms.library.entity.ThongBao;

/**
 * Mapper để convert ThongBao entity thành ThongBaoResponse
 */
@Component
public class ThongBaoMapper {

    /**
     * Convert ThongBao entity thành ThongBaoResponse
     */
    public ThongBaoResponse toThongBaoResponse(ThongBao thongBao) {
        if (thongBao == null) {
            return null;
        }

        return ThongBaoResponse.builder()
                .id(thongBao.getId())
                .tieuDe(thongBao.getTieuDe())
                .noiDung(thongBao.getNoiDung())
                .loaiThongBao(thongBao.getLoaiThongBao())
                .daDoc(thongBao.getDaDoc() != null ? thongBao.getDaDoc() : false)
                .ngayTao(thongBao.getNgayTao())
                .nguoiNhan(thongBao.getNguoiNhan())
                .build();
    }

    /**
     * Convert ThongBao entity thành ThongBaoResponse (cho collection)
     */
    public ThongBaoResponse toThongBaoResponse(ThongBao thongBao, Boolean daDoc) {
        if (thongBao == null) {
            return null;
        }

        return ThongBaoResponse.builder()
                .id(thongBao.getId())
                .tieuDe(thongBao.getTieuDe())
                .noiDung(thongBao.getNoiDung())
                .loaiThongBao(thongBao.getLoaiThongBao())
                .daDoc(daDoc != null ? daDoc : (thongBao.getDaDoc() != null ? thongBao.getDaDoc() : false))
                .ngayTao(thongBao.getNgayTao())
                .nguoiNhan(thongBao.getNguoiNhan())
                .build();
    }
}
