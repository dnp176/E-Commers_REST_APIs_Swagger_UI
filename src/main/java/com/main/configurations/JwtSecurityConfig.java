package com.main.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.main.util.JwtRequestFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    // Flag to enable/disable authentication for all endpoints
    private static final boolean ENABLE_ALL_ENDPOINTS = true;

    // List of publicly accessible endpoints (if authentication is enabled)
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/auth/**",
            "/swagger-ui/**",
            "/api-docs/**",
            "/actuator/**",
            "/actuator/prometheus/**"
    );

    public JwtSecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf().disable();

        if (ENABLE_ALL_ENDPOINTS) {
            // If all endpoints should be open, allow everything
            http.authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll() // Allow access to all endpoints
            );
        } else {
            // Otherwise, restrict access based on PUBLIC_ENDPOINTS list
            http.authorizeHttpRequests(auth -> auth
                    .requestMatchers(PUBLIC_ENDPOINTS.toArray(new String[0])).permitAll() // Allow listed endpoints
                    .anyRequest().authenticated() // Require authentication for all other endpoints
            );
        }

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
