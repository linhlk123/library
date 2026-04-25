package com.lms.library.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.library.entity.DocGia;
import com.lms.library.entity.PhieuMuonTra;
import com.lms.library.enums.TrangThaiPhieu;

@Repository
public interface PhieuMuonTraRepository extends JpaRepository<PhieuMuonTra, Integer> {

    @Query("SELECT p FROM PhieuMuonTra p " +
            "LEFT JOIN FETCH p.docGia " +           
            "LEFT JOIN FETCH p.nhanVien " +        
            "LEFT JOIN FETCH p.cuonSach cs " +
            "LEFT JOIN FETCH cs.sach s " +
            "LEFT JOIN FETCH s.dauSach ds")
    java.util.List<PhieuMuonTra> findAllWithBookInfo();

    @Query("SELECT p FROM PhieuMuonTra p " +
            "LEFT JOIN FETCH p.docGia " +         
            "LEFT JOIN FETCH p.nhanVien " +      
            "LEFT JOIN FETCH p.cuonSach cs " +
            "LEFT JOIN FETCH cs.sach s " +
            "LEFT JOIN FETCH s.dauSach ds " +
            "WHERE p.docGia.maDocGia = :maDocGia")
    java.util.List<PhieuMuonTra> findByMaDocGiaWithBookInfo(@Param("maDocGia") String maDocGia);

    /**
     * Tính tổng tiền phạt của một độc giả từ các phiếu mượn trả
     * Sử dụng COALESCE để trả về 0 nếu không có phiếu nào
     */
    @Query("SELECT COALESCE(SUM(p.tienPhat), 0) FROM PhieuMuonTra p WHERE p.docGia.maDocGia = :maDocGia")
    BigDecimal tinhTongTienPhatByDocGia(@Param("maDocGia") String maDocGia);

    /**
     * Kiểm tra xem độc giả có phiếu nào chưa trả mà ngày phải trả nằm trước ngày
     * hiện tại không
     * Điều này kiểm tra xem độc giả có sách mượn quá hạn hay không
     * 
     * @param docGia Thông tin độc giả
     * @param date   Ngày so sánh (thường là ngày hôm nay)
     * @return true nếu có sách quá hạn, false nếu không
     */
    boolean existsByDocGiaAndNgayTraIsNullAndNgayPhaiTraBefore(DocGia docGia, LocalDate date);

    /**
     * Đếm số lượng sách độc giả đang mượn (chưa trả)
     * 
     * @param docGia Thông tin độc giả
     * @return Số lượng sách đang mượn
     */
    long countByDocGiaAndNgayTraIsNull(DocGia docGia);

    long countByTrangThai(TrangThaiPhieu trangThai);
}