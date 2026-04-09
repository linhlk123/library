package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PHIEUTHUTIENPHAT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuThuTienPhat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SoPTT")
    Integer soPTT;

    @ManyToOne
    @JoinColumn(name = "MaDocGia", nullable = false)
    DocGia docGia;

    @Column(name = "NgayThu", nullable = false)
    LocalDate ngayThu;

    @Column(name = "SoTienThu", precision = 12, scale = 2)
    BigDecimal soTienThu;

    @Column(name = "ConLai", precision = 12, scale = 2)
    BigDecimal conLai;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", referencedColumnName = "TenDangNhap", nullable = false)
    NguoiDung nhanVien;
}