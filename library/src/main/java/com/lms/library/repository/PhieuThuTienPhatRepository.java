package com.lms.library.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.library.entity.PhieuThuTienPhat;

@Repository
public interface PhieuThuTienPhatRepository extends JpaRepository<PhieuThuTienPhat, Integer> {

    /**
     * Load full receipt list with related entities in one SQL to avoid N+1.
     */
    @Query("SELECT DISTINCT pt FROM PhieuThuTienPhat pt " +
            "LEFT JOIN FETCH pt.docGia dg " +
            "LEFT JOIN FETCH dg.nguoiDung ndg " +
            "LEFT JOIN FETCH pt.nhanVien nv " +
            "ORDER BY pt.soPTT DESC")
    List<PhieuThuTienPhat> findAllWithDetails();

        @Query("SELECT pt FROM PhieuThuTienPhat pt " +
            "LEFT JOIN FETCH pt.docGia dg " +
            "LEFT JOIN FETCH dg.nguoiDung ndg " +
            "LEFT JOIN FETCH pt.nhanVien nv " +
            "WHERE pt.soPTT = :soPTT")
        Optional<PhieuThuTienPhat> findByIdWithDetails(@Param("soPTT") Integer soPTT);

    /**
     * Tính tổng tiền thu từ các phiếu thu tiền phạt của một độc giả
     * Sử dụng COALESCE để trả về 0 nếu không có phiếu thu nào
     */
    @Query("SELECT COALESCE(SUM(pt.soTienThu), 0) FROM PhieuThuTienPhat pt WHERE pt.docGia.maDocGia = :maDocGia")
    BigDecimal tinhTongTienThuByDocGia(@Param("maDocGia") String maDocGia);
}