package com.lms.library.mapper;

import com.lms.library.dto.response.DocGiaResponse;
import com.lms.library.entity.DocGia;
import org.springframework.stereotype.Component;

@Component
public class DocGiaMapper {

    public DocGiaResponse toDocGiaResponse(DocGia docGia) {
        if (docGia == null)
            return null;

        return DocGiaResponse.builder()
                .maDocGia(docGia.getMaDocGia())
                .maLoaiDocGia(docGia.getLoaiDocGia() != null ? docGia.getLoaiDocGia().getMaLoaiDocGia() : null)
                .tenLoaiDocGia(docGia.getLoaiDocGia() != null ? docGia.getLoaiDocGia().getTenLoaiDocGia() : null)
                .hoTen(docGia.getHoTen())
                .ngaySinh(docGia.getNgaySinh())
                .diaChi(docGia.getDiaChi())
                .email(docGia.getEmail())
                .ngayLapThe(docGia.getNgayLapThe())
                .ngayHetHan(docGia.getNgayHetHan())
                .tongNo(docGia.getTongNo())
                .tenVaiTro(docGia.getRole() != null ? docGia.getRole().getName() : null)
                .build();
    }
}