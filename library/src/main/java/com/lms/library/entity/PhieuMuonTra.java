package com.lms.library.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.lms.library.enums.TrangThaiPhieu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "PHIEUMUONTRA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuMuonTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SoPhieu")
    Integer soPhieu;

    @ManyToOne
    @JoinColumn(name = "MaCuonSach", nullable = false)
    CuonSach cuonSach;

    @ManyToOne
    @JoinColumn(name = "MaDocGia", nullable = false)
    DocGia docGia;

    @Column(name = "NgayMuon")
    LocalDate ngayMuon;

    @Column(name = "NgayPhaiTra")
    LocalDate ngayPhaiTra;

    @Column(name = "NgayTra")
    LocalDate ngayTra;

    @Column(name = "SoNgayMuon")
    Integer soNgayMuon;

    @Column(name = "TienPhat", precision = 12, scale = 2)
    BigDecimal tienPhat;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", referencedColumnName = "TenDangNhap", nullable = true)
    NguoiDung nhanVien;

    @Column(name = "TrangThai", length = 20)
    @Enumerated(EnumType.STRING)
    TrangThaiPhieu trangThai;
}