package com.lms.library.mapper;
import org.mapstruct.Mapper;
import com.lms.library.dto.request.PhanQuyenRequest;
import com.lms.library.dto.response.PhanQuyenResponse;
import com.lms.library.entity.PhanQuyen;

@Mapper(componentModel = "spring")
public interface PhanQuyenMapper {
    PhanQuyen toPermission(PhanQuyenRequest request);
    PhanQuyenResponse toPermissionResponse(PhanQuyen request);
}
