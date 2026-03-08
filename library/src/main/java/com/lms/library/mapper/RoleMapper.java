package com.lms.library.mapper;
import org.mapstruct.Mapper;

import com.lms.library.dto.request.RoleRequest;
import com.lms.library.dto.response.RoleResponse;
import com.lms.library.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role request);
}