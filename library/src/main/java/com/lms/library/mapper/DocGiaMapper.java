package com.lms.library.mapper;

import org.springframework.stereotype.Component;

import com.lms.library.dto.response.DocGiaResponse;
import com.lms.library.entity.DocGia;

@Component
public class DocGiaMapper {

    public DocGiaResponse toDocGiaResponse(DocGia docGia) {
        if (docGia == null)
            return null;

        return DocGiaResponse.builder()
                .maDocGia(docGia.getMaDocGia())
                .maLoaiDocGia(docGia.getLoaiDocGia() != null ? docGia.getLoaiDocGia().getMaLoaiDocGia() : null)
                .tenLoaiDocGia(docGia.getLoaiDocGia() != null ? docGia.getLoaiDocGia().getTenLoaiDocGia() : null)
                .hoTen(docGia.getNguoiDung() != null ? docGia.getNguoiDung().getHoTen() : null)
                .email(docGia.getNguoiDung() != null ? docGia.getNguoiDung().getEmail() : null)
                .diaChi(docGia.getNguoiDung() != null ? docGia.getNguoiDung().getDiaChi() : null)
                .matKhau(docGia.getNguoiDung() != null ? docGia.getNguoiDung().getMatKhau() : null)
                .tenVaiTro(docGia.getTenVaiTro())
                .ngayLapThe(docGia.getNgayLapThe())
                .ngayHetHan(docGia.getNgayHetHan())
                .tongNo(docGia.getTongNo())
                .build();
    }
}