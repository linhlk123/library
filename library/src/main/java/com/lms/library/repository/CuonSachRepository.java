package com.lms.library.repository;

import com.lms.library.entity.CuonSach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuonSachRepository extends JpaRepository<CuonSach, Integer> {

    List<CuonSach> findBySach_MaSach(Integer maSach);

    boolean existsBySach_MaSach(Integer maSach);
}