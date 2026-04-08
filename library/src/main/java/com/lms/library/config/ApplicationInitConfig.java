// package com.lms.library.config;


// import org.springframework.boot.ApplicationRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.lms.library.entity.VaiTro;
// import com.lms.library.entity.NguoiDung;
// import com.lms.library.repository.VaiTroRepository;


// import lombok.extern.slf4j.Slf4j;

// @Configuration
// @Slf4j 
// public class ApplicationInitConfig {

//     @Bean
//     ApplicationRunner applicationRunner(VaiTroRepository vaiTroRepository, PasswordEncoder passwordEncoder) {
//         return args -> {
//             if (vaiTroRepository.findByName(com.lms.library.enums.Role.ADMIN.name()).isEmpty()) {
                
//                 VaiTro adminRole = VaiTro.builder()
//                         .name(com.lms.library.enums.Role.USER.name())
//                         .build();

//                 NguoiDung user = NguoiDung.builder()
//                         .username("admin")
//                         .password(passwordEncoder.encode("admin123"))
//                         .role(adminRole) 
//                         .build();

//                 userRepository.save(user);
                
//                 log.warn("Admin user created with username 'admin' and password 'admin123', please change the password after first login.");
//             }
//         };
//     }
// }