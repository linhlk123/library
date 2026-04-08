package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "BC_TINHHINHMUONSACH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaoCaoTinhHinhMuonSach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBCTHMS")
    Integer maBCTHMS;

    @Column(name = "Thang", nullable = false)
    Integer thang;

    @Column(name = "Nam", nullable = false)
    Integer nam;

    @Column(name = "TongSoLuotMuon")
    Integer tongSoLuotMuon;
}