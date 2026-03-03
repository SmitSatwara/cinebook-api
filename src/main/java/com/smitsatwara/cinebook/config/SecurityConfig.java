package com.smitsatwara.cinebook.config;

import com.smitsatwara.cinebook.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/movies/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/movies/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/theatres/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/theatres/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/screens/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/screens/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/shows/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/shows/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/seats/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/seats/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/show-seats/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/show-seats/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/show-seats/**").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
