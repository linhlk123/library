package com.lms.library.enums;

/**
 * Loại thông báo
 * 
 * INFO: Thông báo thông thường
 * WARNING: Cảnh báo
 * SUCCESS: Thành công
 */
public enum LoaiThongBao {
    INFO("Thông báo"),
    WARNING("Cảnh báo"),
    SUCCESS("Thành công");

    private final String displayName;

    LoaiThongBao(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
