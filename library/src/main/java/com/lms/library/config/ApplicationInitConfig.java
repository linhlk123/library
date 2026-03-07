package com.lms.library.config;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lms.library.entity.Role;
import com.lms.library.entity.User;
import com.lms.library.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<Role>();
                roles.add(Role.builder().name(com.lms.library.enums.Role.ADMIN.name()).build());
                // Tạo user admin với mật khẩu đã mã hóa
                User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123")) 
                    .roles(roles)
                    .build();
                // Lưu user admin vào cơ sở dữ liệu
                userRepository.save(user);
                log.warn("Admin user created with username 'admin'and password 'admin123', please change the password after first login.");
            }
        };
    }
}
