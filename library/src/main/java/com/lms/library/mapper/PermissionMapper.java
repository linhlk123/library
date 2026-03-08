package com.lms.library.mapper;
import org.mapstruct.Mapper;
import com.lms.library.dto.request.PermissionRequest;
import com.lms.library.dto.response.PermissionResponse;
import com.lms.library.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission request);
}
