package com.lms.library.repository;

import com.lms.library.entity.DocGia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocGiaRepository extends JpaRepository<DocGia, Integer> {
    boolean existsByEmail(String email);
}