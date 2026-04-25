package com.lms.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.entity.Sach;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer> {

    List<Sach> findByDauSach_MaDauSach(Integer maDauSach);

    boolean existsByDauSach_MaDauSach(Integer maDauSach);

    @Modifying
    @Transactional
    @Query("DELETE FROM Sach s WHERE s.dauSach.maDauSach = :maDauSach")
    void deleteAllByMaDauSach(Integer maDauSach);

    /**
     * ============ SOFT DELETE QUERIES ============
     * Sử dụng JPQL Update để thực hiện xóa mềm tối ưu hiệu năng
     * Không fetch dữ liệu lên RAM, update trực tiếp trong database
     */

    /**
     * Soft delete tất cả Sach thuộc một DauSach
     * (Cập nhật isDeleted = true mà không lôi dữ liệu lên)
     *
     * @param maDauSach: Mã đầu sách
     * @return Số bản ghi đã được cập nhật
     */
    @Modifying
    @Transactional
    @Query("UPDATE Sach s SET s.isDeleted = true WHERE s.dauSach.maDauSach = :maDauSach")
    int softDeleteAllByMaDauSach(Integer maDauSach);

    /**
     * Soft delete một Sach cụ thể
     *
     * @param maSach: Mã sách
     * @return Số bản ghi đã được cập nhật
     */
    @Modifying
    @Transactional
    @Query("UPDATE Sach s SET s.isDeleted = true WHERE s.maSach = :maSach")
    int softDeleteByMaSach(Integer maSach);
}