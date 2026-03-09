package com.lms.library.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    SUCCESS(1000, "Operation successful", HttpStatus.OK),
    UNCATEGORIZED_ERROR(1001, "Unknown system error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_CONNECTION_ERROR(1002, "Database connection error", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_ERROR(1003, "Third-party service is unavailable", HttpStatus.BAD_GATEWAY),
    INVALID_FORMAT(1004, "Invalid input data format", HttpStatus.BAD_REQUEST),
    METHOD_NOT_SUPPORTED(1005, "HTTP method not supported", HttpStatus.METHOD_NOT_ALLOWED),
    UNAUTHENTICATED(2001, "User not authenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2002, "Access to this resource is denied", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(2003, "Session has expired", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(2004, "Invalid username or password", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(2005, "Account locked due to multiple failed attempts", HttpStatus.FORBIDDEN),
    ACCOUNT_NOT_ACTIVATED(2006, "Account not activated via email", HttpStatus.FORBIDDEN),
    USER_EXISTED(3001, "This email is already registered", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(3002, "User not found", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(3003, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(4001, "Permission already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(4002, "Permission not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(5001, "Role already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(5002, "Role not found", HttpStatus.NOT_FOUND),
    DOB_INVALID(6001, "Invalid date of birth", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
