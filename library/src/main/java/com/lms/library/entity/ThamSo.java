package com.lms.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "THAMSO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThamSo {

    @Id
    @Column(name = "TenThamSo", length = 100)
    String tenThamSo;

    @Column(name = "GiaTri", length = 100)
    String giaTri;
}