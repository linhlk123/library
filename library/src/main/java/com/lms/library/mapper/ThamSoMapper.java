package com.lms.library.mapper;

import com.lms.library.dto.response.ThamSoResponse;
import com.lms.library.entity.ThamSo;
import org.springframework.stereotype.Component;

@Component
public class ThamSoMapper {

    public ThamSoResponse toThamSoResponse(ThamSo thamSo) {
        if (thamSo == null)
            return null;

        return ThamSoResponse.builder()
                .tenThamSo(thamSo.getTenThamSo())
                .giaTri(thamSo.getGiaTri())
                .build();
    }
}