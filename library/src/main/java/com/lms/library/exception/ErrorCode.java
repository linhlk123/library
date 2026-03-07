package com.lms.library.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    SUCCESS(1000, "Thao tác thành công", HttpStatus.OK),
    UNCATEGORIZED_ERROR(1001, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR), 
    DATABASE_CONNECTION_ERROR(1002, "Lỗi kết nối cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_ERROR(1003, "Dịch vụ bên thứ 3 đang gián đoạn", HttpStatus.BAD_GATEWAY),
    INVALID_FORMAT(1004, "Định dạng dữ liệu đầu vào không hợp lệ", HttpStatus.BAD_REQUEST), 
    METHOD_NOT_SUPPORTED(1005, "Phương thức HTTP không được hỗ trợ", HttpStatus.METHOD_NOT_ALLOWED), 
    UNAUTHENTICATED(2001, "Chưa xác thực người dùng", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2002, "Không có quyền truy cập tài nguyên này", HttpStatus.FORBIDDEN), 
    TOKEN_EXPIRED(2003, "Phiên đăng nhập đã hết hạn", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(2004, "Tên đăng nhập hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(2005, "Tài khoản đã bị khóa do nhập sai nhiều lần", HttpStatus.FORBIDDEN),   
    ACCOUNT_NOT_ACTIVATED(2006, "Tài khoản chưa được kích hoạt qua email", HttpStatus.FORBIDDEN),
    USER_EXISTED(3001, "Email này đã được đăng ký", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(3002, "Không tìm thấy thông tin người dùng", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(3003, "Mật khẩu phải có ít nhất 8 ký tự", HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }


}
