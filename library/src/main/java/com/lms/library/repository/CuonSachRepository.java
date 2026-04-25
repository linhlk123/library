package com.lms.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.entity.CuonSach;

@Repository
public interface CuonSachRepository extends JpaRepository<CuonSach, Integer> {

    List<CuonSach> findBySach_MaSach(Integer maSach);

    boolean existsBySach_MaSach(Integer maSach);

    /**
     * ============ SOFT DELETE QUERIES ============
     * Sử dụng JPQL Update để thực hiện xóa mềm tối ưu hiệu năng
     * Không fetch dữ liệu lên RAM, update trực tiếp trong database
     */

    /**
     * Soft delete tất cả CuonSach thuộc một Sach
     * (Cập nhật isDeleted = true mà không lôi dữ liệu lên)
     *
     * @param maSach: Mã sách
     * @return Số bản ghi đã được cập nhật
     */
    @Modifying
    @Transactional
    @Query("UPDATE CuonSach c SET c.isDeleted = true WHERE c.sach.maSach = :maSach")
    int softDeleteAllByMaSach(Integer maSach);

    /**
     * Soft delete tất cả CuonSach liên quan đến DauSach
     * Dùng subquery để tìm tất cả Sach của DauSach rồi xóa mềm CuonSach
     * (Hiệu năng cao - một câu query duy nhất)
     *
     * @param maDauSach: Mã đầu sách
     * @return Số bản ghi đã được cập nhật
     */
    @Modifying
    @Transactional
    @Query("UPDATE CuonSach c SET c.isDeleted = true WHERE c.sach.maSach IN " +
            "(SELECT s.maSach FROM Sach s WHERE s.dauSach.maDauSach = :maDauSach)")
    int softDeleteAllByMaDauSach(Integer maDauSach);

    /**
     * Soft delete một CuonSach cụ thể
     *
     * @param maCuonSach: Mã cuốn sách
     * @return Số bản ghi đã được cập nhật
     */
    @Modifying
    @Transactional
    @Query("UPDATE CuonSach c SET c.isDeleted = true WHERE c.maCuonSach = :maCuonSach")
    int softDeleteByMaCuonSach(Integer maCuonSach);
}