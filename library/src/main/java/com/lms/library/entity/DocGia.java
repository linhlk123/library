package com.lms.library.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
    @Column(name = "MaDocGia")
    String maDocGia; // Primary Key - Derived from NguoiDung.tenDangNhap

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "MaDocGia", referencedColumnName = "tenDangNhap")
    NguoiDung nguoiDung; // 1-1 FK with Shared PK - Reference to NGUOIDUNG table

    @ManyToOne
    @JoinColumn(name = "MaLoaiDocGia", nullable = false)
    LoaiDocGia loaiDocGia; // n-1 Foreign Key - Reference to LOAIDOCGIA table

    @Column(name = "NgayLapThe")
    LocalDate ngayLapThe; // Card issuance date

    @Column(name = "NgayHetHan")
    LocalDate ngayHetHan; // Card expiration date

    @Column(name = "TongNo", precision = 12, scale = 2)
    BigDecimal tongNo; // Fine amount for overdue/damaged books

    @Column(name = "TenVaiTro", length = 50)
    String tenVaiTro; // Role name (stored snapshot from NGUOIDUNG.vaiTro)

}