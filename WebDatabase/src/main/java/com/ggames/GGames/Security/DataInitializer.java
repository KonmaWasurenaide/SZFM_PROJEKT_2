package com.ggames.GGames.Security;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A(z) {@code DataInitializer} osztály egy Spring {@code Configuration},
 * amely felelős az alkalmazás indításakor szükséges kezdeti adatok beállításáért.
 * <p>
 * Konkrétan egy alapértelmezett "admin" felhasználót hoz létre,
 * ha az még nem létezik az adatbázisban.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    /**
     * A {@link UserRepository} a felhasználói adatok elérésére szolgál
     * az admin felhasználó létezésének ellenőrzéséhez és mentéséhez.
     */
    private final UserRepository userRepository;

    /**
     * A {@link PasswordEncoder} a jelszavak biztonságos hashelésére szolgál
     * az admin felhasználó létrehozásakor.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Létrehoz egy {@link CommandLineRunner} beant, amely az alkalmazás
     * teljes kontextusának betöltése után azonnal lefut.
     * <p>
     * Ez a metódus ellenőrzi, hogy létezik-e az "admin" nevű felhasználó.
     * Ha nem, létrehoz egy alapértelmezett admin felhasználót
     * (felhasználónév: "admin", jelszó: "admin" [titkosítva], szerepkör: "ADMIN").
     * </p>
     *
     * @return Egy {@link CommandLineRunner} példány, amely elindítja az admin inicializációs logikát.
     */
    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(UserEntity.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .password(passwordEncoder.encode("admin"))
                        .role("ADMIN")
                        .build());
                System.out.println("Admin user created: admin / admin");
            }
        };
    }
}