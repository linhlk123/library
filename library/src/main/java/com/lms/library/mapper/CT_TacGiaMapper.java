package com.lms.library.mapper;

import com.lms.library.dto.response.CT_TacGiaResponse;
import com.lms.library.entity.CT_TacGia;
import org.springframework.stereotype.Component;

@Component
public class CT_TacGiaMapper {

    public CT_TacGiaResponse toCT_TacGiaResponse(CT_TacGia ctTacGia) {
        if (ctTacGia == null)
            return null;

        return CT_TacGiaResponse.builder()
                .maDauSach(ctTacGia.getDauSach().getMaDauSach())
                .tenDauSach(ctTacGia.getDauSach().getTenDauSach())
                .maTacGia(ctTacGia.getTacGia().getMaTacGia())
                .tenTacGia(ctTacGia.getTacGia().getTenTacGia())
                .build();
    }
}