package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "DAUSACH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DauSach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDauSach")
    Integer maDauSach;

    @Column(name = "TenDauSach", nullable = false, length = 200)
    String tenDauSach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTheLoai", nullable = false)
    TheLoai theLoai;
}