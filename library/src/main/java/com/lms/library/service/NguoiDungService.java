package com.lms.library.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.library.dto.request.NguoiDungCreationRequest;
import com.lms.library.dto.request.NguoiDungUpdateRequest;
import com.lms.library.dto.response.NguoiDungResponse;
import com.lms.library.entity.VaiTro;
import com.lms.library.entity.NguoiDung;
import com.lms.library.exception.AppException;
import com.lms.library.exception.ErrorCode;
import com.lms.library.mapper.NguoiDungMapper;
import com.lms.library.repository.NguoiDungRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NguoiDungService {
    NguoiDungRepository userRepository;
    NguoiDungMapper userMapper;
    PasswordEncoder passwordEncoder;

    public NguoiDungResponse createUser(NguoiDungCreationRequest request) {
        if (userRepository.existsByTenDangNhap(request.getTenDangNhap())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Map request to user entity
        NguoiDung user = userMapper.toUser(request);
        // Encrypt password
        user.setMatKhau(passwordEncoder.encode(request.getMatKhau()));

        VaiTro vaiTro = new VaiTro();
        vaiTro.setTenVaiTro(request.getTenVaiTro());
        user.setVaiTro(vaiTro);

        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_STAFF')")
    public List<NguoiDungResponse> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.tenDangNhap == authentication.name or hasAuthority('ADMIN')")
    public NguoiDungResponse getUserById(String id) {
        log.info("Getting user by ID: {}", id);
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_STAFF') and #id == authentication.name")
    public NguoiDungResponse updateUser(String id, NguoiDungUpdateRequest request) {
        log.info("Updating user: {}", id);
        NguoiDung user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_STAFF') and #id == authentication.name")
    public void deleteUser(String id) {
        NguoiDung user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
