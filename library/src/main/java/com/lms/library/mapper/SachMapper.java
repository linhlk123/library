package com.lms.library.mapper;

import com.lms.library.dto.response.SachResponse;
import com.lms.library.entity.Sach;
import org.springframework.stereotype.Component;

@Component
public class SachMapper {

    public SachResponse toSachResponse(Sach sach) {
        if (sach == null)
            return null;

        return SachResponse.builder()
                .maSach(sach.getMaSach())
                .maDauSach(sach.getDauSach().getMaDauSach())
                .tenDauSach(sach.getDauSach().getTenDauSach())
                .maTheLoai(sach.getDauSach().getTheLoai().getMaTheLoai())
                .tenTheLoai(sach.getDauSach().getTheLoai().getTenTheLoai())
                .nhaXuatBan(sach.getNhaXuatBan())
                .namXuatBan(sach.getNamXuatBan())
                .soLuong(sach.getSoLuong())
                .giaTien(sach.getGiaTien())
                .build();
    }
}