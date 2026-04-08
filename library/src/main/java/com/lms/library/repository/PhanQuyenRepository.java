package com.lms.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.library.entity.PhanQuyen;

@Repository
public interface PhanQuyenRepository extends JpaRepository<PhanQuyen, String> {

    public boolean existsByTenQuyen(String tenQuyen);

}
