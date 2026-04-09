package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DOCGIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDocGia")
    Integer maDocGia;

    @ManyToOne
    @JoinColumn(name = "MaLoaiDocGia", nullable = false)
    LoaiDocGia loaiDocGia;

    @Column(name = "HoTen", nullable = false, length = 100)
    String hoTen;

    @Column(name = "NgaySinh")
    LocalDate ngaySinh;

    @Column(name = "DiaChi", length = 255)
    String diaChi;

    @Column(name = "Email", length = 100)
    String email;

    @Column(name = "MatKhau", nullable = false, length = 255)
    String matKhau;

    @Column(name = "NgayLapThe")
    LocalDate ngayLapThe;

    @Column(name = "NgayHetHan")
    LocalDate ngayHetHan;

    @Column(name = "TongNo", precision = 12, scale = 2)
    BigDecimal tongNo;

    // @ManyToOne
    // @JoinColumn(name = "TenVaiTro", referencedColumnName = "name")
    // Role role;

    @ManyToOne
    @JoinColumn(name = "TenVaiTro", referencedColumnName = "tenVaiTro")
    VaiTro role;
}