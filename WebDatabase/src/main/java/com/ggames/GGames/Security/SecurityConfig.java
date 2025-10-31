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

/**
 * A(z) {@code SecurityConfig} osztály a Spring Security konfigurációját valósítja meg.
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
     * Létrehoz egy {@code PasswordEncoder} beant a jelszavak biztonságos hashelésére.
     * <p>
     * A {@link BCryptPasswordEncoder} használatát ajánljuk a jelszavak tárolásához.
     * </p>
     *
     * @return A titkosító objektum.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Definiálja a Spring Security szűrő láncot (Filter Chain).
     * <p>
     * Ez a metódus konfigurálja a jogosultsági szabályokat, a bejelentkezési
     * és kijelentkezési URL-eket, valamint a CSRF (Cross-Site Request Forgery) védelmet.
     * </p>
     * <ul>
     * <li>**Jogosultságok:**</li>
     * <ul>
     * <li>A {@code /register}, {@code /login} és más statikus/publikus források (pl. {@code /h2-console/**}) mindenki számára elérhetőek.</li>
     * <li>A {@code /admin/**} útvonalak csak az "ADMIN" szerepkörrel rendelkezőknek engedélyezettek (a {@code hasRole} automatikusan hozzáadja a "ROLE_" prefixet).</li>
     * <li>Minden más kéréshez hitelesítés (bejelentkezés) szükséges ({@code anyRequest().authenticated()}).</li>
     * </ul>
     * <li>**Bejelentkezés:** Form alapú bejelentkezés, amely a {@code /login} URL-t használja feldolgozásra.</li>
     * <li>**CSRF:** Letiltja a CSRF-et a {@code /h2-console/**} útvonalon, ami szükséges az H2 adatbázis webes konzoljának használatához.</li>
     * <li>**Headers:** Letiltja a frame-options védelmet, ami szintén szükséges a H2 konzol iframe-ben történő megjelenítéséhez.</li>
     * </ul>
     *
     * @param http Az {@link HttpSecurity} objektum a biztonsági konfiguráció beállításához.
     * @return Egy konfigurált {@link SecurityFilterChain} objektum.
     * @throws Exception Ha hiba történik a konfiguráció során.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/h2-console/**", "/css/**",
                                "/login.html", "/kuspgames.html", "/kuspgames.css", "/kuspgames.js").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/kuspgames.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", false)
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
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    /**
     * Létrehoz egy {@link AuthenticationManager} beant, amely a Spring Security
     * autentikációs folyamatának kezeléséért felelős.
     * <p>
     * Ez a bean szükséges lehet külső autentikációs szolgáltatások (pl. JWT)
     * használatához a későbbi fázisokban.
     * </p>
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