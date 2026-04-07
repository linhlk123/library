package com.lms.library.repository;

import com.lms.library.entity.LoaiDocGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiDocGiaRepository extends JpaRepository<LoaiDocGia, Integer> {
    boolean existsByTenLoaiDocGia(String tenLoaiDocGia);
}