package com.example.Demo_E_Commerce.config;

import com.example.Demo_E_Commerce.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class Security {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(config ->
                        config
                                .requestMatchers("/auth/**").permitAll()
//                                .requestMatchers("/", "/index.html", "/login.html", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/cart/**").hasRole("USER")
                                .requestMatchers("/admin-dashboard.html").hasRole("ADMIN")
                                .requestMatchers("/user-products.html").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/product", "/category").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/category").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e ->
                        e.accessDeniedHandler(((request, response, accessDeniedException) -> response.sendRedirect("/login.html"))))
                .build();

    }
}
