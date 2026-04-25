package com.lms.library.service.example;

import com.lms.library.service.ThamSoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Example: Cách sử dụng ThamSoService trong các Service khác
 * 
 * Ví dụ này hướng dẫn cách gọi các getter methods từ ThamSoService
 * để lấy giá trị tham số và sử dụng trong logic nghiệp vụ.
 */
@Service
@RequiredArgsConstructor
public class ExampleServiceUsage {

    private final ThamSoService thamSoService;

    /**
     * Ví dụ 1: Validate tuổi độc giả
     */
    public void validateDocGiaAge(Integer age) {
        Integer tuoiMin = thamSoService.getTuoiToiThieu();
        Integer tuoiMax = thamSoService.getTuoiToiDa();

        if (age < tuoiMin || age > tuoiMax) {
            throw new IllegalArgumentException(
                    String.format("Tuổi phải từ %d đến %d", tuoiMin, tuoiMax));
        }
    }

    /**
     * Ví dụ 2: Validate số ngày mượn sách
     */
    public void validateSoNgayMuon(Integer soNgay) {
        Integer soNgayToiDa = thamSoService.getSoNgayMuonToiDa();

        if (soNgay > soNgayToiDa) {
            throw new IllegalArgumentException(
                    String.format("Số ngày mượn tối đa là %d ngày", soNgayToiDa));
        }
    }

    /**
     * Ví dụ 3: Validate số sách mượn
     */
    public void validateSoSachMuon(Integer soSach) {
        Integer soSachToiDa = thamSoService.getSoSachMuonToiDa();

        if (soSach > soSachToiDa) {
            throw new IllegalArgumentException(
                    String.format("Số sách mượn tối đa là %d cuốn", soSachToiDa));
        }
    }

    /**
     * Ví dụ 4: Validate năm xuất bản sách
     */
    public void validateNamXuatBan(Integer namXB) {
        int currentYear = java.time.Year.now().getValue();
        Integer khoangCach = thamSoService.getKhoangCachNamXB();

        if (currentYear - namXB > khoangCach) {
            throw new IllegalArgumentException(
                    String.format("Sách không được cũ quá %d năm (năm xuất bản: %d)",
                            khoangCach, namXB));
        }
    }

    /**
     * Ví dụ 5: Tính tiền phạt
     */
    public Long calculateLateFee(Integer dayLate) {
        Long tienPhatToiDa = thamSoService.getTienPhatToiDa();
        return tienPhatToiDa * dayLate;
    }

    /**
     * Ví dụ 6: Kiểm tra có áp dụng quy định kiểm tra số tiền thu không
     */
    public void processPayment(Long tienThu) {
        Boolean apDung = thamSoService.isApDungQDKiemTraSoTienThu();

        if (apDung) {
            // Áp dụng quy định kiểm tra
            Long tienPhatToiDa = thamSoService.getTienPhatToiDa();

            if (tienThu > tienPhatToiDa * 10) { // Ví dụ: không vượt quá 10 lần tiền phạt tối đa
                throw new IllegalArgumentException(
                        "Số tiền thu không hợp lệ theo quy định");
            }
        }
        // Nếu không áp dụng, không cần kiểm tra
    }

    /**
     * Ví dụ 7: Validate thẻ độc giả hết hạn
     */
    public void validateTheDocGia(java.time.LocalDate ngayCapThe) {
        Integer thoiHanThe = thamSoService.getThoiHanThe();
        java.time.LocalDate ngayHetHan = ngayCapThe.plusMonths(thoiHanThe);
        java.time.LocalDate today = java.time.LocalDate.now();

        if (today.isAfter(ngayHetHan)) {
            throw new IllegalStateException(
                    String.format("Thẻ độc giả hết hạn từ ngày %s", ngayHetHan));
        }
    }

    /**
     * Ví dụ 8: Cách sử dụng tất cả các tham số cùng lúc
     */
    public void validateComplexBorrowingRules(
            Integer ageDocGia,
            Integer soNgayMuon,
            Integer soSachMuon,
            Integer namXBSach,
            java.time.LocalDate ngayCapThe) {
        // Validate tuổi
        validateDocGiaAge(ageDocGia);

        // Validate thẻ
        validateTheDocGia(ngayCapThe);

        // Validate số ngày mượn
        validateSoNgayMuon(soNgayMuon);

        // Validate số sách
        validateSoSachMuon(soSachMuon);

        // Validate năm xuất bản
        validateNamXuatBan(namXBSach);

        System.out.println("✓ Tất cả điều kiện mượn sách đều hợp lệ");
    }
}

/**
 * ===========================
 * QUICK REFERENCE
 * ===========================
 * 
 * Getter Methods:
 * ✓ thamSoService.getTuoiToiThieu() → Integer: 18
 * ✓ thamSoService.getTuoiToiDa() → Integer: 55
 * ✓ thamSoService.getThoiHanThe() → Integer: 6 (tháng)
 * ✓ thamSoService.getKhoangCachNamXB() → Integer: 8 (năm)
 * ✓ thamSoService.getSoNgayMuonToiDa() → Integer: 4 (ngày)
 * ✓ thamSoService.getSoSachMuonToiDa() → Integer: 5 (sách)
 * ✓ thamSoService.getTienPhatToiDa() → Long: 1000 (VNĐ/ngày)
 * ✓ thamSoService.isApDungQDKiemTraSoTienThu() → Boolean: true/false
 * 
 * ===========================
 */
