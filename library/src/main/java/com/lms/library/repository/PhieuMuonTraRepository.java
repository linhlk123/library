package com.lms.library.repository;

import com.lms.library.entity.PhieuMuonTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuMuonTraRepository extends JpaRepository<PhieuMuonTra, Integer> {
}