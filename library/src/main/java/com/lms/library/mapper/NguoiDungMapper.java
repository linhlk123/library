package com.lms.library.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.lms.library.dto.request.NguoiDungCreationRequest;
import com.lms.library.dto.request.NguoiDungUpdateRequest;
import com.lms.library.dto.response.NguoiDungResponse;
import com.lms.library.entity.NguoiDung;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface NguoiDungMapper {

    @Mapping(target = "vaiTro", ignore = true)
    @Mapping(target = "tenDangNhap", source = "tenDangNhap")
    @Mapping(target = "matKhau", source = "matKhau")
    NguoiDung toUser(NguoiDungCreationRequest request);

    @Mapping(target = "vaiTro", source = "vaiTro.tenVaiTro")
    NguoiDungResponse toUserResponse(NguoiDung user);

    @Mapping(target = "vaiTro", ignore = true)
    void updateUser(@MappingTarget NguoiDung user, NguoiDungUpdateRequest request);

}
