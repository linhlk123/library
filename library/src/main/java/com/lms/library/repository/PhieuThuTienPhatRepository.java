package com.lms.library.repository;

import com.lms.library.entity.PhieuThuTienPhat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuThuTienPhatRepository extends JpaRepository<PhieuThuTienPhat, Integer> {
}