package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "SACH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSach")
    Integer maSach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDauSach", nullable = false)
    DauSach dauSach;

    @Column(name = "NhaXuatBan", length = 150)
    String nhaXuatBan;

    @Column(name = "NamXuatBan")
    Integer namXuatBan;

    @Column(name = "SoLuong", columnDefinition = "INT DEFAULT 0")
    Integer soLuong;

    @Column(name = "GiaTien", precision = 12, scale = 2, columnDefinition = "DECIMAL(12,2) DEFAULT 0")
    BigDecimal giaTien;
}