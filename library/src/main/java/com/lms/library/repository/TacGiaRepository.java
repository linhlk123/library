package com.lms.library.repository;

import com.lms.library.entity.TacGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacGiaRepository extends JpaRepository<TacGia, Integer> {

    boolean existsByTenTacGiaIgnoreCase(String tenTacGia);
}