package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "CT_BC_TINHHINHMUONSACH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CTBC_THMSId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CTBC_THMS {

    @Id
    @ManyToOne
    @JoinColumn(name = "MaBCTHMS", nullable = false)
    BaoCaoTinhHinhMuonSach baoCaoTinhHinhMuonSach;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaTheLoai", nullable = false)
    TheLoai theLoai;

    @Column(name = "SoLuotMuon")
    Integer soLuotMuon;

    @Column(name = "TiLe", precision = 5, scale = 2)
    BigDecimal tiLe;
}