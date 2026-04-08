package com.lms.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lms.library.dto.request.VaiTroRequest;
import com.lms.library.dto.response.VaiTroResponse;
import com.lms.library.entity.VaiTro;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {
    @Mapping(target = "danhSachPhanQuyen", source = "phanQuyen")
    VaiTro toRole(VaiTroRequest request);

    VaiTroResponse toRoleResponse(VaiTro request);
}