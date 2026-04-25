package com.lms.library.mapper;

import org.springframework.stereotype.Component;

import com.lms.library.dto.response.DauSachResponse;
import com.lms.library.entity.DauSach;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DauSachMapper {

    final TheLoaiMapper theLoaiMapper;

    public DauSachResponse toDauSachResponse(DauSach dauSach) {
        if (dauSach == null)
            return null;

        return DauSachResponse.builder()
                .maDauSach(dauSach.getMaDauSach())
                .tenDauSach(dauSach.getTenDauSach())
                .theLoai(theLoaiMapper.toTheLoaiResponse(dauSach.getTheLoai()))
                .anhBiaUrl(dauSach.getAnhBiaUrl())
                .build();
    }
}