package com.lms.library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.dto.request.ApiResponse;
import com.lms.library.dto.request.ThongBaoCreationRequest;
import com.lms.library.dto.response.ThongBaoResponse;
import com.lms.library.service.ThongBaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller xử lý API thông báo
 * 
 * Endpoints:
 * GET /api/thongbao/me - Lấy danh sách thông báo của user đang đăng nhập
 * GET /api/thongbao/me/unread-count - Đếm thông báo chưa đọc
 * GET /api/thongbao/{id} - Lấy chi tiết thông báo
 * POST /api/thongbao - Tạo thông báo mới (admin only)
 * PUT /api/thongbao/{id}/read - Đánh dấu thông báo đã đọc
 * PUT /api/thongbao/read-all - Đánh dấu tất cả thông báo đã đọc
 * DELETE /api/thongbao/{id} - Xóa thông báo
 */
@Slf4j
@RestController
@RequestMapping("/api/thongbao")
@RequiredArgsConstructor
public class ThongBaoController {

    private final ThongBaoService thongBaoService;

    /**
     * Lấy tên người dùng từ SecurityContext
     */
    private String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "UNKNOWN";
    }

    /**
     * GET /api/thongbao/me
     * Lấy danh sách thông báo của user đang đăng nhập
     * (Bao gồm: thông báo riêng + broadcast)
     * 
     * @return ApiResponse<List<ThongBaoResponse>>
     */
    @GetMapping("/me")
    public ApiResponse<List<ThongBaoResponse>> getMyNotifications() {
        String username = getCurrentUsername();

        log.info("GET /api/thongbao/me - User: {}", username);

        List<ThongBaoResponse> notifications = thongBaoService.getThongBaoByNguoiNhan(username);

        return ApiResponse.<List<ThongBaoResponse>>builder()
                .code(1000)
                .message("Lấy danh sách thông báo thành công")
                .result(notifications)
                .build();
    }

    /**
     * GET /api/thongbao/me/unread-count
     * Đếm số thông báo chưa đọc của user đang đăng nhập
     * 
     * @return ApiResponse<Map> (chứa unreadCount)
     */
    @GetMapping("/me/unread-count")
    public ApiResponse<Map<String, Long>> getUnreadCount() {
        String username = getCurrentUsername();

        long unreadCount = thongBaoService.countUnreadNotifications(username);

        Map<String, Long> result = new HashMap<>();
        result.put("unreadCount", unreadCount);

        log.info("GET /api/thongbao/me/unread-count - User: {}, Unread: {}", username, unreadCount);

        return ApiResponse.<Map<String, Long>>builder()
                .code(1000)
                .message("Đếm thông báo chưa đọc thành công")
                .result(result)
                .build();
    }

    /**
     * GET /api/thongbao/{id}
     * Lấy chi tiết một thông báo
     * 
     * @param id: ID thông báo
     * @return ApiResponse<ThongBaoResponse>
     */
    @GetMapping("/{id}")
    public ApiResponse<ThongBaoResponse> getNotificationById(@PathVariable Long id) {
        log.info("GET /api/thongbao/{} - ID: {}", id, id);

        ThongBaoResponse notification = thongBaoService.getThongBaoById(id);

        return ApiResponse.<ThongBaoResponse>builder()
                .code(1000)
                .message("Lấy thông báo thành công")
                .result(notification)
                .build();
    }

    /**
     * PUT /api/thongbao/{id}/read
     * Đánh dấu một thông báo là đã đọc
     * 
     * @param id: ID thông báo
     * @return ApiResponse<Void>
     */
    @PutMapping("/{id}/read")
    public ApiResponse<Void> markAsRead(@PathVariable Long id) {
        String username = getCurrentUsername();

        log.info("PUT /api/thongbao/{}/read - User: {}, ID: {}", id, username, id);

        thongBaoService.markAsRead(id);

        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Đánh dấu thông báo đã đọc thành công")
                .result(null)
                .build();
    }

    /**
     * PUT /api/thongbao/read-all
     * Đánh dấu tất cả thông báo của user là đã đọc
     * 
     * @return ApiResponse<Map> (chứa markedCount)
     */
    @PutMapping("/read-all")
    public ApiResponse<Map<String, Integer>> markAllAsRead() {
        String username = getCurrentUsername();

        log.info("PUT /api/thongbao/read-all - User: {}", username);

        int markedCount = thongBaoService.markAllAsRead(username);

        Map<String, Integer> result = new HashMap<>();
        result.put("markedCount", markedCount);

        return ApiResponse.<Map<String, Integer>>builder()
                .code(1000)
                .message("Đánh dấu tất cả thông báo đã đọc thành công")
                .result(result)
                .build();
    }

    /**
     * POST /api/thongbao
     * Tạo thông báo mới (admin only)
     * 
     * @param request: ThongBaoCreationRequest
     * @return ApiResponse<ThongBaoResponse>
     */
    @PostMapping
    public ApiResponse<ThongBaoResponse> createNotification(@RequestBody ThongBaoCreationRequest request) {
        String username = getCurrentUsername();

        log.info("POST /api/thongbao - User: {}, Loại: {}, NguoiNhan: {}",
                username, request.getLoaiThongBao(), request.getNguoiNhan());

        ThongBaoResponse notification = thongBaoService.createThongBao(request);

        return ApiResponse.<ThongBaoResponse>builder()
                .code(1000)
                .message("Tạo thông báo thành công")
                .result(notification)
                .build();
    }

    /**
     * POST /api/thongbao/broadcast
     * Gửi thông báo broadcast cho tất cả user
     * 
     * @param request: ThongBaoCreationRequest
     * @return ApiResponse<ThongBaoResponse>
     */
    @PostMapping("/broadcast")
    public ApiResponse<ThongBaoResponse> sendBroadcast(@RequestBody ThongBaoCreationRequest request) {
        String username = getCurrentUsername();

        log.info("POST /api/thongbao/broadcast - User: {}, Loại: {}",
                username, request.getLoaiThongBao());

        ThongBaoResponse notification = thongBaoService.sendBroadcastNotification(request);

        return ApiResponse.<ThongBaoResponse>builder()
                .code(1000)
                .message("Gửi thông báo broadcast thành công")
                .result(notification)
                .build();
    }

    /**
     * DELETE /api/thongbao/{id}
     * Xóa một thông báo
     * 
     * @param id: ID thông báo
     * @return ApiResponse<Void>
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNotification(@PathVariable Long id) {
        String username = getCurrentUsername();

        log.info("DELETE /api/thongbao/{} - User: {}, ID: {}", id, username, id);

        thongBaoService.deleteThongBao(id);

        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa thông báo thành công")
                .result(null)
                .build();
    }
}
