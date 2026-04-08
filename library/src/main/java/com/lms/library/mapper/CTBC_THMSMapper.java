package com.lms.library.mapper;

import com.lms.library.dto.response.CTBC_THMSResponse;
import com.lms.library.entity.CTBC_THMS;
import org.springframework.stereotype.Component;

@Component
public class CTBC_THMSMapper {

    public CTBC_THMSResponse toResponse(CTBC_THMS ctbcThms) {
        if (ctbcThms == null)
            return null;

        return CTBC_THMSResponse.builder()
                .maBCTHMS(ctbcThms.getBaoCaoTinhHinhMuonSach().getMaBCTHMS())
                .maTheLoai(ctbcThms.getTheLoai().getMaTheLoai())
                .tenTheLoai(ctbcThms.getTheLoai().getTenTheLoai())
                .soLuotMuon(ctbcThms.getSoLuotMuon())
                .tiLe(ctbcThms.getTiLe())
                .build();
    }
}