package com.lms.library.mapper;

import com.lms.library.dto.response.TacGiaResponse;
import com.lms.library.entity.TacGia;
import org.springframework.stereotype.Component;

@Component
public class TacGiaMapper {

    public TacGiaResponse toTacGiaResponse(TacGia tacGia) {
        if (tacGia == null)
            return null;

        return TacGiaResponse.builder()
                .maTacGia(tacGia.getMaTacGia())
                .tenTacGia(tacGia.getTenTacGia())
                .ngaySinh(tacGia.getNgaySinh())
                .build();
    }
}