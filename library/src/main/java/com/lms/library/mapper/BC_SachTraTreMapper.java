package com.lms.library.mapper;

import com.lms.library.dto.response.BC_SachTraTreResponse;
import com.lms.library.entity.BC_SachTraTre;
import org.springframework.stereotype.Component;

@Component
public class BC_SachTraTreMapper {

    public BC_SachTraTreResponse toResponse(BC_SachTraTre bcSachTraTre) {
        if (bcSachTraTre == null)
            return null;

        return BC_SachTraTreResponse.builder()
                .ngay(bcSachTraTre.getNgay())
                .maCuonSach(bcSachTraTre.getCuonSach().getMaCuonSach())
                .maSach(bcSachTraTre.getCuonSach().getSach().getMaSach())
                .tenDauSach(bcSachTraTre.getCuonSach().getSach().getDauSach().getTenDauSach())
                .ngayMuon(bcSachTraTre.getNgayMuon())
                .soNgayTraTre(bcSachTraTre.getSoNgayTraTre())
                .build();
    }
}