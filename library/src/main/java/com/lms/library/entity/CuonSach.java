package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "CUONSACH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CuonSach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCuonSach")
    Integer maCuonSach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSach", nullable = false)
    Sach sach;

    @Column(name = "TinhTrang", length = 100)
    String tinhTrang;

    @Column(name = "IsDeleted", columnDefinition = "BIT DEFAULT 0")
    Boolean isDeleted;
}