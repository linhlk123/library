package com.lms.library.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lms.library.entity.VaiTro;
import com.lms.library.entity.NguoiDung;
import com.lms.library.repository.VaiTroRepository;
import com.lms.library.repository.NguoiDungRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(VaiTroRepository vaiTroRepository,
            NguoiDungRepository nguoiDungRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            String staffRoleName = com.lms.library.enums.VaiTro.STAFF.name();

            VaiTro staffVaiTro = vaiTroRepository.findByTenVaiTro(staffRoleName)
                    .orElseGet(() -> vaiTroRepository.save(VaiTro.builder()
                            .tenVaiTro(staffRoleName)
                            .build()));

            if (!nguoiDungRepository.existsByTenDangNhap("staff")) {
                NguoiDung user = NguoiDung.builder()
                        .tenDangNhap("staff")
                        .matKhau(passwordEncoder.encode("Staff@123"))
                        .vaiTro(staffVaiTro)
                        .build();
                nguoiDungRepository.save(user);

                log.warn(
                        "Staff user created with username 'staff' and password 'Staff@123', please change the password after first login.");
            }
        };
    }
}