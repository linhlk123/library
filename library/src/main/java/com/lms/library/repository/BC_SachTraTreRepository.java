package com.lms.library.repository;

import com.lms.library.entity.BC_SachTraTre;
import com.lms.library.entity.BC_SachTraTreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BC_SachTraTreRepository extends JpaRepository<BC_SachTraTre, BC_SachTraTreId> {

    List<BC_SachTraTre> findByNgay(LocalDate ngay);

    List<BC_SachTraTre> findByCuonSach_MaCuonSach(Integer maCuonSach);
}