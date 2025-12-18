package com.projectBackend.GMotors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(cs -> cs.disable())
            .cors(cors -> cors.configure(http))
            .authorizeHttpRequests(auth -> auth

                // ============================
                //   RUTAS PÚBLICAS (SIN TOKEN)
                // ============================
                .requestMatchers("/api/usuarios/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() // Registro
                .requestMatchers("/images/**").permitAll()

                // ============================
                //   RUTAS PROTEGIDAS
                // ============================
                .requestMatchers("/api/usuarios/**").authenticated()
                .requestMatchers("/api/motos/**").authenticated()

                // Cualquier otra ruta, requiere token
                .anyRequest().authenticated()
            )

            // Filtro JWT ANTES del filtro de autenticación
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
}

