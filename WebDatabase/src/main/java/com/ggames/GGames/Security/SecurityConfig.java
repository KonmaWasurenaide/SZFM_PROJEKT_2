package com.ggames.GGames.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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

/**
 * Az alkalmazás Spring Security konfigurációját kezeli.
 *
 * <p>Definiálja a hitelesítési menedzsert, a jelszókódolót és a biztonsági szűrő láncot,
 * beleértve az útvonalakhoz tartozó jogosultsági szabályokat (például mely útvonalak engedélyezettek
 * a nem hitelesített felhasználók számára, és melyekhez szükséges a bejelentkezés vagy az ADMIN szerepkör).</p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Egyedi szolgáltatás a felhasználói adatok betöltéséhez a hitelesítési folyamat során.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * Bean a jelszavak hashelésére és ellenőrzésére.
     *
     * <p>A {@code BCryptPasswordEncoder} használata erősen ajánlott a jelszavak biztonságos tárolásához.</p>
     *
     * @return A {@code BCryptPasswordEncoder} példánya.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean a Spring Security hitelesítési menedzserének (AuthenticationManager) biztosítására.
     *
     * <p>Ez az objektum kezeli a hitelesítési kérelmeket.</p>
     *
     * @param config Az {@code AuthenticationConfiguration} a menedzser konfigurálásához.
     * @return Az {@code AuthenticationManager} példánya.
     * @throws Exception Ha hiba történik a konfiguráció során.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Definiálja a biztonsági szűrő láncot, amely az alkalmazás összes HTTP kérését kezeli.
     *
     * <p>Konfigurálja a jogosultsági szabályokat, a bejelentkezési formát, a kijelentkezési logikát,
     * valamint a CSRF és a header beállításokat (különös tekintettel a {@code h2-console} és {@code ws} útvonalakra).</p>
     *
     * @param http A {@code HttpSecurity} objektum a biztonsági konfiguráláshoz.
     * @return A konfigurált {@code SecurityFilterChain}.
     * @throws Exception Ha hiba történik a konfiguráció során.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/home", "/register", "/login", "/game/**", "/play/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers(
                                "/library/**",
                                "/download/**",
                                "/protected-images/**",
                                "/api/users/send-request/**",
                                "/api/friendships/**",
                                "/friends/**",
                                "/notifications",
                                "/chat/**"
                        ).authenticated()
                        .requestMatchers(
                                "/admin/**",
                                "/h2-console/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/ws/**")
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}