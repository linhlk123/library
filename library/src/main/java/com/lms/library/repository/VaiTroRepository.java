package com.lms.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.library.entity.VaiTro;

public interface VaiTroRepository extends JpaRepository<VaiTro, String> {

    boolean existsByTenVaiTro(String tenVaiTro);

}
