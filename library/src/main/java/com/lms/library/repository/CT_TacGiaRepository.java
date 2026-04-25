package com.lms.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.library.entity.CT_TacGia;
import com.lms.library.entity.CT_TacGiaId;

@Repository
public interface CT_TacGiaRepository extends JpaRepository<CT_TacGia, CT_TacGiaId> {

    List<CT_TacGia> findByDauSach_MaDauSach(Integer maDauSach);

    List<CT_TacGia> findByTacGia_MaTacGia(Integer maTacGia);

    boolean existsByDauSach_MaDauSachAndTacGia_MaTacGia(Integer maDauSach, Integer maTacGia);

    @Modifying
    @Transactional
    @Query("DELETE FROM CT_TacGia ct WHERE ct.dauSach.maDauSach = :maDauSach")
    void deleteAllByMaDauSach(Integer maDauSach);

    @Query("SELECT ct FROM CT_TacGia ct JOIN FETCH ct.tacGia WHERE ct.dauSach.maDauSach IN :maDauSachIds")
    List<CT_TacGia> findAllByDauSachIds(@Param("maDauSachIds") List<Integer> maDauSachIds);
}