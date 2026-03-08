    package com.lms.library.config;
    import javax.crypto.SecretKey;
    import javax.crypto.spec.SecretKeySpec;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
    import org.springframework.security.oauth2.jwt.JwtDecoder;
    import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
    import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
    import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
    import org.springframework.security.web.SecurityFilterChain;

    import com.lms.library.enums.Role;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity(prePostEnabled = true)
    public class SecurityConfig {

        //Định nghĩa các endpoint công khai không yêu cầu xác thực
        private final String[] PUBLIC_ENDPOINTS = {
                "/api/v1/users",
                "/api/v1/auth/token",
                "/api/v1/auth/introspect"
        };
        
        //Lấy secret key từ file cấu hình application.properties
        @Value("${jwt.signer-key}")
        private String SIGNER_KEY;

        //Cấu hình PasswordEncoder để mã hóa mật khẩu người dùng
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        //Cấu hình các endpoint công khai và bảo vệ các endpoint khác
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyAuthority(Role.ADMIN.name())
                    .anyRequest().authenticated());
            http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwtConfigurer -> jwtConfigurer
                    .decoder(jwtDecoder())
                    .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
            
            http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

            http.csrf(csrf -> csrf.disable());
            return http.build();
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
            JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            grantedAuthoritiesConverter.setAuthorityPrefix("");
            JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
            return jwtAuthenticationConverter;
        }

        //Dùng secret key để mã hóa và giải mã JWT
        @Bean
        public JwtDecoder jwtDecoder() {
            SecretKey key = new SecretKeySpec(SIGNER_KEY.getBytes(), "HmacSHA512");
            //NimbusJwtDecoder sử dụng khóa bí mật để giải mã JWT
            return NimbusJwtDecoder
                    .withSecretKey(key)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

    }