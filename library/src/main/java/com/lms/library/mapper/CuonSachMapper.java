package com.lms.library.mapper;

import com.lms.library.dto.response.CuonSachResponse;
import com.lms.library.entity.CuonSach;
import org.springframework.stereotype.Component;

@Component
public class CuonSachMapper {

    public CuonSachResponse toCuonSachResponse(CuonSach cuonSach) {
        if (cuonSach == null)
            return null;

        return CuonSachResponse.builder()
                .maCuonSach(cuonSach.getMaCuonSach())
                .maSach(cuonSach.getSach().getMaSach())
                .maDauSach(cuonSach.getSach().getDauSach().getMaDauSach())
                .tenDauSach(cuonSach.getSach().getDauSach().getTenDauSach())
                .nhaXuatBan(cuonSach.getSach().getNhaXuatBan())
                .namXuatBan(cuonSach.getSach().getNamXuatBan())
                .tinhTrang(cuonSach.getTinhTrang())
                .build();
    }
}