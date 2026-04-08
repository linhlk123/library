package com.lms.library.mapper;

import com.lms.library.dto.response.BaoCaoTinhHinhMuonSachResponse;
import com.lms.library.entity.BaoCaoTinhHinhMuonSach;
import org.springframework.stereotype.Component;

@Component
public class BaoCaoTinhHinhMuonSachMapper {

    public BaoCaoTinhHinhMuonSachResponse toResponse(BaoCaoTinhHinhMuonSach baoCao) {
        if (baoCao == null)
            return null;

        return BaoCaoTinhHinhMuonSachResponse.builder()
                .maBCTHMS(baoCao.getMaBCTHMS())
                .thang(baoCao.getThang())
                .nam(baoCao.getNam())
                .tongSoLuotMuon(baoCao.getTongSoLuotMuon())
                .build();
    }
}