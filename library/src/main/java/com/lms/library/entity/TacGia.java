package com.lms.library.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "TACGIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TacGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTacGia")
    Integer maTacGia;

    @Column(name = "TenTacGia", nullable = false, length = 100)
    String tenTacGia;

    @Column(name = "NgaySinh")
    LocalDate ngaySinh;
}