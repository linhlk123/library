package com.lms.library.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lms.library.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception ex) {
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                                            .code(ErrorCode.UNCATEGORIZED_ERROR.getCode())
                                            .message(ErrorCode.UNCATEGORIZED_ERROR.getMessage())
                                            .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Void>> handlingAppException(AppException ex) {
    ErrorCode errorCode = ex.getErrorCode();
    ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                                            .code(errorCode.getCode())
                                            .message(errorCode.getMessage())
                                            .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handlingValidationException(MethodArgumentNotValidException ex) {
        String enumKey = ex.getFieldError().getDefaultMessage();

        // Lấy mã lỗi từ enum dựa trên khóa
        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            errorCode = ErrorCode.UNCATEGORIZED_ERROR;
        }
        // Tạo phản hồi API với mã lỗi và thông điệp tương ứng
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                                            .code(errorCode.getCode())
                                            .message(errorCode.getMessage())
                                            .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
