package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "CT_TACGIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CT_TacGiaId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CT_TacGia {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDauSach", nullable = false)
    DauSach dauSach;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTacGia", nullable = false)
    TacGia tacGia;
}