package com.lms.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.entity.ThongBao;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long> {

    /**
     * Lấy danh sách thông báo của user
     * Bao gồm: thông báo riêng + thông báo broadcast (ALL)
     * Sắp xếp theo ngày tạo giảm dần (mới nhất lên đầu)
     * 
     * @param username: Tên người dùng
     * @return Danh sách thông báo
     */
    @Query("SELECT t FROM ThongBao t WHERE (t.nguoiNhan = :username OR t.nguoiNhan = 'ALL') " +
            "ORDER BY t.ngayTao DESC")
    List<ThongBao> findByNguoiNhanOrderByNgayTaoDesc(String username);

    /**
     * Đếm số thông báo chưa đọc (unread) của user
     * 
     * @param username: Tên người dùng
     * @return Số lượng thông báo chưa đọc
     */
    @Query("SELECT COUNT(t) FROM ThongBao t WHERE (t.nguoiNhan = :username OR t.nguoiNhan = 'ALL') " +
            "AND t.daDoc = false")
    long countUnreadByNguoiNhan(String username);

    /**
     * Đánh dấu một thông báo là đã đọc
     * 
     * @param id: ID thông báo
     */
    @Modifying
    @Transactional
    @Query("UPDATE ThongBao t SET t.daDoc = true WHERE t.id = :id")
    void markAsRead(Long id);

    /**
     * Đánh dấu tất cả thông báo của user là đã đọc
     * 
     * @param username: Tên người dùng
     * @return Số thông báo được cập nhật
     */
    @Modifying
    @Transactional
    @Query("UPDATE ThongBao t SET t.daDoc = true WHERE (t.nguoiNhan = :username OR t.nguoiNhan = 'ALL') " +
            "AND t.daDoc = false")
    int markAllAsReadByNguoiNhan(String username);

    /**
     * Xóa thông báo cũ (theo thời gian)
     * Dùng cho maintenance
     * 
     * @param days: Số ngày (xóa thông báo cũ hơn X ngày)
     * @return Số thông báo đã xóa
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM THONGBAO WHERE DATEDIFF(DAY, ngay_tao, GETDATE()) > :days", nativeQuery = true)
    int deleteOldNotifications(int days);
}
