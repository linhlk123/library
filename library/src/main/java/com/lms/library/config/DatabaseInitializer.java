package com.lms.library.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lms.library.entity.PhanQuyen;
import com.lms.library.entity.VaiTro;
import com.lms.library.repository.PhanQuyenRepository;
import com.lms.library.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final VaiTroRepository vaiTroRepository;
    private final PhanQuyenRepository phanQuyenRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!vaiTroRepository.existsByTenVaiTro("USER")) {
            VaiTro userRole = new VaiTro();
            userRole.setTenVaiTro("USER");
            vaiTroRepository.save(userRole);
        }

        if (!vaiTroRepository.existsByTenVaiTro("DOCGIA")) {
            VaiTro docGiaRole = new VaiTro();
            docGiaRole.setTenVaiTro("DOCGIA");
            vaiTroRepository.save(docGiaRole);
        }

        if (!phanQuyenRepository.existsByTenQuyen("READ_PRIVILEGE")) {
            PhanQuyen readPrivilege = new PhanQuyen();
            readPrivilege.setTenQuyen("READ_PRIVILEGE");
            phanQuyenRepository.save(readPrivilege);
        }

        if (!phanQuyenRepository.existsByTenQuyen("WRITE_PRIVILEGE")) {
            PhanQuyen writePrivilege = new PhanQuyen();
            writePrivilege.setTenQuyen("WRITE_PRIVILEGE");
            phanQuyenRepository.save(writePrivilege);
        }
    }
    
}
