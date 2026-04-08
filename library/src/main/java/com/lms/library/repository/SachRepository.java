package com.lms.library.repository;

import com.lms.library.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer> {

    List<Sach> findByDauSach_MaDauSach(Integer maDauSach);

    boolean existsByDauSach_MaDauSach(Integer maDauSach);
}