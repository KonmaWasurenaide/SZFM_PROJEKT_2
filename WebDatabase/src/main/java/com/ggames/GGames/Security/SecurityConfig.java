package com.ggames.GGames.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

/**
 * A {@code SecurityConfig} osztály a Spring Security konfigurációját valósítja meg.
 * <p>
 * Ez az osztály definiálja a biztonsági szűrő láncot (filter chain), a jogosultsági
 * szabályokat, az autentikációs mechanizmusokat (form login, logout)
 * és a jelszó titkosítási algoritmust ({@code PasswordEncoder}).
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * A {@link CustomUserDetailsService} a felhasználói adatok ({@code UserDetails})
     * adatbázisból történő betöltéséért felelős.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * Létrehoz egy {@code PasswordEncoder} beant a jelszavak biztonságos hashelésére (BCrypt).
     *
     * @return A titkosító objektum.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Definiálja a Spring Security szűrő láncot (Filter Chain), amely meghatározza a jogosultsági szabályokat és a viselkedést.
     *
     * @param http Az {@link HttpSecurity} objektum a biztonsági konfiguráció beállításához.
     * @return Egy konfigurált {@link SecurityFilterChain} objektum.
     * @throws Exception Ha hiba történik a konfiguráció során.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/home", "/register", "/login", "/game/**", "/play/**").permitAll()
                        .requestMatchers("/library/**", "/download/**", "/protected-images/**").authenticated()
                        .requestMatchers("/movies/crud", "/actors/**",
                                "/movies/edit/**", "/actors/edit/**", "/rentals/all/**").hasAnyRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/h2-console/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // Feldolgozás explicit megadása
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Szükséges az H2 Console iframe működéséhez


        return http.build();
    }

    /**
     * Létrehoz egy {@link AuthenticationManager} beant, amely szükséges lehet más Spring komponensekhez.
     *
     * @param config Az {@link AuthenticationConfiguration} a menedzser konfigurálásához.
     * @return A konfigurált {@link AuthenticationManager} objektum.
     * @throws Exception Ha hiba történik az autentikációs menedzser létrehozásakor.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}