package com.lms.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.library.dto.request.VaiTroRequest;
import com.lms.library.dto.response.VaiTroResponse;
import com.lms.library.entity.VaiTro;
import com.lms.library.exception.AppException;
import com.lms.library.exception.ErrorCode;
import com.lms.library.mapper.VaiTroMapper;
import com.lms.library.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VaiTroService {

    VaiTroRepository vaiTroRepository;
    VaiTroMapper vaiTroMapper;

    public VaiTroResponse create(VaiTroRequest request) {
        if (vaiTroRepository.existsByTenVaiTro(request.getTenVaiTro())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        VaiTro role = vaiTroMapper.toRole(request);
        VaiTro savedRole = vaiTroRepository.save(role);
        return vaiTroMapper.toRoleResponse(savedRole);
    }

    public List<VaiTroResponse> getAll() {
        return vaiTroRepository.findAll().stream().map(vaiTroMapper::toRoleResponse).toList();
    }

    public void delete(String name) {
        VaiTro role = vaiTroRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        vaiTroRepository.delete(role);
    }
}
