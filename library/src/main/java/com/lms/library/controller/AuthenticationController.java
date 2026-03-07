package com.lms.library.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.AuthenticationRequest;
import com.lms.library.dto.request.IntrospectRequest;
import com.lms.library.dto.response.AuthenticationResponse;
import com.lms.library.dto.response.IntrospectResponse;
import com.lms.library.exception.ErrorCode;
import com.lms.library.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    // Endpoint để xác thực người dùng và cấp token JWT
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .code(ErrorCode.SUCCESS.getCode())
                .build();
    }
    
    // Endpoint để kiểm tra tính hợp lệ của token JWT
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
                throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .code(ErrorCode.SUCCESS.getCode())
                .build();
    }
}