package com.lms.library.mapper;

import com.lms.library.dto.request.PhieuThuTienPhatCreationRequest;
import com.lms.library.dto.request.PhieuThuTienPhatUpdateRequest;
import com.lms.library.dto.response.PhieuThuTienPhatResponse;
import com.lms.library.entity.PhieuThuTienPhat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhieuThuTienPhatMapper {

    PhieuThuTienPhat toPhieuThuTienPhat(PhieuThuTienPhatCreationRequest request);

    void updatePhieuThuTienPhat(@MappingTarget PhieuThuTienPhat phieuThuTienPhat,
            PhieuThuTienPhatUpdateRequest request);

    PhieuThuTienPhatResponse toPhieuThuTienPhatResponse(PhieuThuTienPhat phieuThuTienPhat);
}