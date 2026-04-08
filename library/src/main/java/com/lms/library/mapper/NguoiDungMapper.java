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
    NguoiDung toUser(NguoiDungCreationRequest request);

    NguoiDungResponse toUserResponse(NguoiDung user);

    @Mapping(target = "vaiTro", ignore = true)
    void updateUser(@MappingTarget NguoiDung user, NguoiDungUpdateRequest request);

}
