package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security para NeoArsenal.
 *
 * DECISIÓN DE DISEÑO (QA/Seguridad — OWASP A07, A02):
 * - Se declara el bean BCryptPasswordEncoder (factor de coste 10) para
 *   hashear y verificar contraseñas de forma segura.
 * - Se deshabilita CSRF (la API REST usa JSON stateless, sin sesiones de servidor).
 * - Se configura permitAll() para mantener el comportamiento actual de la API
 *   sin romper ningún endpoint. La autorización de rutas es responsabilidad
 *   del AuthGuard de Angular en el Frontend.
 * - Se declara un UserDetailsService vacío para suprimir el WARN de
 *   contraseña autogenerada por Spring Security (no se usa para autenticación).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Bean de codificación de contraseñas con BCrypt (factor de coste 10).
     * Se inyecta automáticamente por Spring en UsuariosServiceImp.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * UserDetailsService vacío — declarado para suprimir el WARN de Spring
     * Security sobre la contraseña autogenerada en consola. La autenticación
     * real se gestiona manualmente en UsuariosServiceImp con BCrypt.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    /**
     * Cadena de filtros de seguridad.
     * - Deshabilita CSRF: no necesario en APIs REST stateless.
     * - Permite todas las peticiones: la API mantiene su comportamiento actual.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
