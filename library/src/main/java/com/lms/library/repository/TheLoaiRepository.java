package com.lms.library.repository;

import com.lms.library.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer> {

    boolean existsByTenTheLoaiIgnoreCase(String tenTheLoai);
}