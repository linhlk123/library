package com.lms.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lms.library.entity.DocGia;

public interface DocGiaRepository extends JpaRepository<DocGia, String> {

    @Query("SELECT d FROM DocGia d WHERE d.nguoiDung.tenDangNhap = :tenDangNhap")
    Optional<DocGia> findByTenDangNhap(@Param("tenDangNhap") String tenDangNhap);
}