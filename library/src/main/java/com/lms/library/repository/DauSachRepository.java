package com.lms.library.repository;

import com.lms.library.entity.DauSach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DauSachRepository extends JpaRepository<DauSach, Integer> {

    List<DauSach> findByTheLoai_MaTheLoai(Integer maTheLoai);

    boolean existsByTheLoai_MaTheLoai(Integer maTheLoai);

}