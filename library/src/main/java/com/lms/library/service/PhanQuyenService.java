package com.lms.library.service;

import java.util.List;

import com.lms.library.repository.PhanQuyenRepository;

import org.springframework.stereotype.Service;

import com.lms.library.dto.request.PhanQuyenRequest;
import com.lms.library.dto.response.PhanQuyenResponse;
import com.lms.library.entity.PhanQuyen;
import com.lms.library.exception.AppException;
import com.lms.library.exception.ErrorCode;
import com.lms.library.mapper.PhanQuyenMapper;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PhanQuyenService {

    PhanQuyenRepository permissionRepository;
    PhanQuyenMapper permissionMapper;

    public PhanQuyenResponse create(PhanQuyenRequest request) {
        if (permissionRepository.existsByTenQuyen(request.getTenQuyen())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        PhanQuyen permission = permissionMapper.toPermission(request);
        PhanQuyen savedPermission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(savedPermission);
    }

    public List<PhanQuyenResponse> getAll() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String name) {
        PhanQuyen permission = permissionRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.delete(permission);
    }
}
