package com.lms.library.repository;

import com.lms.library.entity.CTBC_THMS;
import com.lms.library.entity.CTBC_THMSId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTBC_THMSRepository extends JpaRepository<CTBC_THMS, CTBC_THMSId> {

    List<CTBC_THMS> findByBaoCaoTinhHinhMuonSach_MaBCTHMS(Integer maBCTHMS);
}