package com.lms.library.entity;

import java.time.LocalDateTime;

import com.lms.library.enums.LoaiThongBao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "THONGBAO", indexes = {
        @Index(name = "idx_nguoinhan_dadoc", columnList = "nguoi_nhan,da_doc"),
        @Index(name = "idx_nguoinhan_ngaytao", columnList = "nguoi_nhan,ngay_tao")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongBao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Long id;

    @Column(name = "TieuDe", nullable = false, length = 255)
    String tieuDe;

    @Column(name = "NoiDung", columnDefinition = "NVARCHAR(MAX)")
    String noiDung;

    @Enumerated(EnumType.STRING)
    @Column(name = "LoaiThongBao", nullable = false)
    LoaiThongBao loaiThongBao;

    @Column(name = "DaDoc", columnDefinition = "BIT DEFAULT 0")
    Boolean daDoc;

    @Column(name = "NgayTao", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    LocalDateTime ngayTao;

    @Column(name = "NguoiNhan", nullable = false, length = 255)
    String nguoiNhan; // username hoặc 'ALL' cho broadcast
}
