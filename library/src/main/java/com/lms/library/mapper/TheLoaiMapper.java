package com.lms.library.mapper;

import com.lms.library.dto.response.TheLoaiResponse;
import com.lms.library.entity.TheLoai;
import org.springframework.stereotype.Component;

@Component
public class TheLoaiMapper {

    public TheLoaiResponse toTheLoaiResponse(TheLoai theLoai) {
        if (theLoai == null)
            return null;

        return TheLoaiResponse.builder()
                .maTheLoai(theLoai.getMaTheLoai())
                .tenTheLoai(theLoai.getTenTheLoai())
                .build();
    }
}