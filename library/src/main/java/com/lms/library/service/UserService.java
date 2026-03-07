package com.lms.library.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.library.dto.request.UserCreationRequest;
import com.lms.library.dto.request.UserUpdateRequest;
import com.lms.library.dto.response.UserResponse;
import com.lms.library.entity.Role;
import com.lms.library.entity.User;
import com.lms.library.exception.AppException;
import com.lms.library.exception.ErrorCode;
import com.lms.library.mapper.UserMapper;
import com.lms.library.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    //khai báo repository và mapper
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        //Map request vào user entity
        User user = userMapper.toUser(request);
        //Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().name(com.lms.library.enums.Role.USER.name()).build());
        user.setRoles(roles);
         
        user =  userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
    // Phương thức lấy danh sách tất cả người dùng
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
    // Phương thức lấy thông tin người dùng theo ID
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);  
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}

