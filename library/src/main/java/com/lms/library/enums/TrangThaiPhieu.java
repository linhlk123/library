package com.lms.library.enums;

public enum TrangThaiPhieu {
    PENDING("PENDING", "Chờ xác nhận"),
    ACTIVE("ACTIVE", "Đang mượn"),
    REJECTED("REJECTED", "Từ chối"),
    RETURNED("RETURNED", "Đã trả");

    private final String code;
    private final String description;

    TrangThaiPhieu(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
