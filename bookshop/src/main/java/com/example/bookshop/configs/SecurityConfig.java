package com.example.bookshop.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS configuration
                .and()
                .csrf().disable() // Disable CSRF for simplicity; consider enabling for stateful applications
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll() // Allow unauthenticated access to public endpoints
                .anyRequest().authenticated() // Other endpoints will require authentication
                .and()
                .httpBasic(); // Enable basic authentication

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000")); // Allow your frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Allowed HTTP methods
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Allowed headers
        config.setAllowCredentials(true); // If you need to send cookies or HTTP authentication

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}