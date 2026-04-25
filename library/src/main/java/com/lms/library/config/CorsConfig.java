    package com.lms.library.config;

    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Bean;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import org.springframework.web.filter.CorsFilter;


    @Configuration
    public class CorsConfig {
        @Bean
        public CorsFilter corsFilter() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://localhost:5173");  // Vite dev
            config.addAllowedOrigin("http://localhost:3000");  // nếu dùng CRA
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
        }
    }