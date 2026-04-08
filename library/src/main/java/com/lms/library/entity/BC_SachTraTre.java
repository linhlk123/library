package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "BC_SACHTRATRE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BC_SachTraTreId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BC_SachTraTre {

    @Id
    @Column(name = "Ngay", nullable = false)
    LocalDate ngay;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaCuonSach", nullable = false)
    CuonSach cuonSach;

    @Column(name = "NgayMuon")
    LocalDate ngayMuon;

    @Column(name = "SoNgayTraTre")
    Integer soNgayTraTre;
}