package com.lms.library.repository;

import com.lms.library.entity.CT_TacGia;
import com.lms.library.entity.CT_TacGiaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CT_TacGiaRepository extends JpaRepository<CT_TacGia, CT_TacGiaId> {

    List<CT_TacGia> findByDauSach_MaDauSach(Integer maDauSach);

    List<CT_TacGia> findByTacGia_MaTacGia(Integer maTacGia);

    boolean existsByDauSach_MaDauSachAndTacGia_MaTacGia(Integer maDauSach, Integer maTacGia);
}