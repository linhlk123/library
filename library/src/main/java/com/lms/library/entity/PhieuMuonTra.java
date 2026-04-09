package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "NgayMuon", nullable = false)
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
    @JoinColumn(name = "MaNhanVien", referencedColumnName = "TenDangNhap", nullable = false)
    NguoiDung nhanVien;
}