package com.lms.library.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.dto.request.ThongBaoCreationRequest;
import com.lms.library.dto.response.ThongBaoResponse;
import com.lms.library.entity.ThongBao;
import com.lms.library.mapper.ThongBaoMapper;
import com.lms.library.repository.ThongBaoRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * Service xử lý thông báo (notification)
 * 
 * Chức năng:
 * - Tạo thông báo mới (cho user hoặc broadcast)
 * - Lấy danh sách thông báo của user
 * - Đếm thông báo chưa đọc
 * - Đánh dấu thông báo đã đọc (1 hoặc tất cả)
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongBaoService {

    ThongBaoRepository thongBaoRepository;
    ThongBaoMapper thongBaoMapper;

    /**
     * Tạo thông báo mới
     * 
     * @param request: Request chứa tieuDe, noiDung, loaiThongBao, nguoiNhan
     * @return ThongBaoResponse
     */
    @Transactional
    public ThongBaoResponse createThongBao(ThongBaoCreationRequest request) {
        ThongBao thongBao = ThongBao.builder()
                .tieuDe(request.getTieuDe())
                .noiDung(request.getNoiDung())
                .loaiThongBao(request.getLoaiThongBao())
                .daDoc(false)
                .ngayTao(LocalDateTime.now())
                .nguoiNhan(request.getNguoiNhan())
                .build();

        ThongBao saved = thongBaoRepository.save(thongBao);
        log.info("✓ Tạo thông báo ID={} cho {}", saved.getId(), saved.getNguoiNhan());

        return thongBaoMapper.toThongBaoResponse(saved);
    }

    /**
     * Lấy danh sách thông báo của user
     * Bao gồm: thông báo riêng + broadcast (ALL)
     * Sắp xếp: mới nhất lên đầu
     * 
     * @param username: Tên người dùng
     * @return Danh sách ThongBaoResponse
     */
    @Transactional(readOnly = true)
    public List<ThongBaoResponse> getThongBaoByNguoiNhan(String username) {
        List<ThongBao> thongBaoList = thongBaoRepository.findByNguoiNhanOrderByNgayTaoDesc(username);

        log.info("✓ Lấy {} thông báo cho {}", thongBaoList.size(), username);

        return thongBaoList.stream()
                .map(thongBaoMapper::toThongBaoResponse)
                .toList();
    }

    /**
     * Đếm số thông báo chưa đọc của user
     * 
     * @param username: Tên người dùng
     * @return Số lượng thông báo chưa đọc
     */
    @Transactional(readOnly = true)
    public long countUnreadNotifications(String username) {
        long count = thongBaoRepository.countUnreadByNguoiNhan(username);

        log.debug("Số thông báo chưa đọc của {}: {}", username, count);

        return count;
    }

    /**
     * Đánh dấu một thông báo là đã đọc
     * 
     * @param id: ID thông báo
     */
    @Transactional
    public void markAsRead(Long id) {
        ThongBao thongBao = thongBaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));

        if (thongBao.getDaDoc() != null && thongBao.getDaDoc()) {
            log.debug("Thông báo ID={} đã được đánh dấu đọc trước đó", id);
            return;
        }

        thongBaoRepository.markAsRead(id);
        log.info("✓ Đánh dấu thông báo ID={} đã đọc", id);
    }

    /**
     * Đánh dấu tất cả thông báo của user là đã đọc
     * 
     * @param username: Tên người dùng
     * @return Số thông báo được đánh dấu
     */
    @Transactional
    public int markAllAsRead(String username) {
        int count = thongBaoRepository.markAllAsReadByNguoiNhan(username);

        log.info("✓ Đánh dấu {} thông báo đã đọc cho {}", count, username);

        return count;
    }

    /**
     * Lấy chi tiết một thông báo
     * 
     * @param id: ID thông báo
     * @return ThongBaoResponse
     */
    @Transactional(readOnly = true)
    public ThongBaoResponse getThongBaoById(Long id) {
        ThongBao thongBao = thongBaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));

        return thongBaoMapper.toThongBaoResponse(thongBao);
    }

    /**
     * Xóa một thông báo
     * 
     * @param id: ID thông báo
     */
    @Transactional
    public void deleteThongBao(Long id) {
        ThongBao thongBao = thongBaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));

        thongBaoRepository.delete(thongBao);
        log.info("✓ Xóa thông báo ID={}", id);
    }

    /**
     * Xóa tất cả thông báo của user
     * 
     * @param username: Tên người dùng
     */
    @Transactional
    public void deleteAllByNguoiNhan(String username) {
        List<ThongBao> thongBaoList = thongBaoRepository.findByNguoiNhanOrderByNgayTaoDesc(username);

        thongBaoRepository.deleteAll(thongBaoList);
        log.info("✓ Xóa {} thông báo cho {}", thongBaoList.size(), username);
    }

    /**
     * Gửi thông báo broadcast cho tất cả user
     * 
     * @param request: Request chứa tieuDe, noiDung, loaiThongBao
     * @return ThongBaoResponse
     */
    @Transactional
    public ThongBaoResponse sendBroadcastNotification(ThongBaoCreationRequest request) {
        // Override nguoiNhan thành 'ALL'
        request.setNguoiNhan("ALL");

        return createThongBao(request);
    }
}
