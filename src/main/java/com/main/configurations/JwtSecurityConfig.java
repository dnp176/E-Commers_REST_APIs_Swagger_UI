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

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig{


	private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    public JwtSecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/api-docs/**")
//                .permitAll()  // Allow access to Swagger and auth APIs without authentication
//                .anyRequest().authenticated()  // All other requests need to be authenticated
//            )
//            .requiresChannel()  // Forces HTTPS for all requests
//            .anyRequest().requiresSecure();  // Enforces HTTPS for every request
//
//        // Add JWT filter before UsernamePasswordAuthenticationFilter
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf().disable()
        .authorizeHttpRequests(auth -> auth
                
                .requestMatchers("/api/auth/**", "/swagger-ui/**","/api-docs/**")
                .permitAll()
                .requestMatchers("/api/auth/**").permitAll() 
                .anyRequest().authenticated() 
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    	//----------------------------------------------------
//    	http.csrf().disable()  // CSRF ko disable karte hai
//        .authorizeHttpRequests(auth -> auth
//            .anyRequest().permitAll()  // Har request ko permitAll() set kar diya, authentication required nahi hai
//        )
//        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Filter abhi bhi available hoga, lekin har request permitAll hogi

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
