package com.sbx.project_database.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig { //without cors configuration it doesnt run on chrome

    @Value("${CORS_ALLOWED_ORIGIN:http://localhost:8080}") // Default to localhost for development
    private String allowedOrigin;

    @Bean
    public CorsFilter corsFilter() {

        // Inject the allowed origin from an environment variable


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of(allowedOrigin));

        config.addAllowedOrigin("http://localhost:8080"); // For web
        config.addAllowedOrigin("http://127.0.0.1:3000");
        config.addAllowedOrigin("http://192.168.1.10:8080");

        config.addAllowedOrigin("http://localhost:3000"); // Flutter web
        config.addAllowedOrigin("http://localhost:5555"); // Flutter web debug
        config.addAllowedOrigin("http://localhost:61124"); // Flutter desktop


        config.addAllowedOrigin("http://localhost");
        config.addAllowedOrigin("http://127.0.0.1");
        config.addAllowedOriginPattern("*"); // For mobile apps
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}