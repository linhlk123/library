package com.lms.library.config;

import com.lms.library.entity.ThamSo;
import com.lms.library.repository.ThamSoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * ThamSoDataSeeder: Khởi tạo dữ liệu tham số khi ứng dụng khởi chạy.
 * 
 * Kiểm tra bảng THAMSO, nếu chưa có dữ liệu thì tự động chèn 8 bản ghi
 * định nghĩa các quy định nghiệp vụ cho hệ thống Thư viện.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ThamSoDataSeeder implements CommandLineRunner {

    private final ThamSoRepository thamSoRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("=============== Starting ThamSoDataSeeder ===============");

        // Kiểm tra nếu bảng THAMSO đã có dữ liệu
        if (thamSoRepository.count() > 0) {
            log.info("ThamSo data already exists. Skipping seeding.");
            return;
        }

        log.info("Initializing ThamSo (Business Rules Parameters)...");

        List<ThamSo> thamSoList = Arrays.asList(
                // Tuổi tối thiểu của độc giả
                ThamSo.builder()
                        .tenThamSo("TuoiToiThieu")
                        .giaTri("18")
                        .build(),

                // Tuổi tối đa của độc giả
                ThamSo.builder()
                        .tenThamSo("TuoiToiDa")
                        .giaTri("55")
                        .build(),

                // Thời hạn thẻ độc giả (tháng)
                ThamSo.builder()
                        .tenThamSo("ThoiHanThe")
                        .giaTri("6")
                        .build(),

                // Khoảng cách năm xuất bản (sách quá cũ không được mượn)
                ThamSo.builder()
                        .tenThamSo("KhoangCachNamXB")
                        .giaTri("8")
                        .build(),

                // Số ngày mượn tối đa cho một phiếu mượn
                ThamSo.builder()
                        .tenThamSo("SoNgayMuonToiDa")
                        .giaTri("4")
                        .build(),

                // Số sách mượn tối đa trong một lần mượn
                ThamSo.builder()
                        .tenThamSo("SoSachMuonToiDa")
                        .giaTri("5")
                        .build(),

                // Tiền phạt tối đa (VNĐ/ngày)
                ThamSo.builder()
                        .tenThamSo("TienPhatToiDa")
                        .giaTri("1000")
                        .build(),

                // Áp dụng Quy Định Kiểm Tra Số Tiền Thu (1: Áp dụng, 0: Không)
                ThamSo.builder()
                        .tenThamSo("ApDungQDKiemTraSoTienThu")
                        .giaTri("1")
                        .build());

        try {
            thamSoRepository.saveAll(thamSoList);
            log.info("✓ Successfully initialized 8 ThamSo records");
            log.info("  - TuoiToiThieu: 18");
            log.info("  - TuoiToiDa: 55");
            log.info("  - ThoiHanThe: 6 months");
            log.info("  - KhoangCachNamXB: 8 years");
            log.info("  - SoNgayMuonToiDa: 4 days");
            log.info("  - SoSachMuonToiDa: 5 books");
            log.info("  - TienPhatToiDa: 1000 VND/day");
            log.info("  - ApDungQDKiemTraSoTienThu: 1 (enabled)");
        } catch (Exception e) {
            log.error("Error seeding ThamSo data", e);
        }

        log.info("=============== ThamSoDataSeeder completed ===============");
    }
}
