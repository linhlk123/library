package com.lms.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.library.entity.NguoiDung;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, String> {
    boolean existsByTenDangNhap(String tenDangNhap);

    Optional<NguoiDung> findByTenDangNhap(String tenDangNhap);
}
