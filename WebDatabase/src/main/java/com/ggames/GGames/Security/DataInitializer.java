package com.ggames.GGames.Security;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Konfigurációs osztály az alkalmazás indításakor szükséges kezdeti adatok beállításához.
 *
 * <p>Felelős az alapértelmezett ADMIN és TESZT USER felhasználók létrehozásáért,
 * ha azok még nem léteznek az adatbázisban.</p>
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Létrehoz egy {@link CommandLineRunner} beant, amely az alkalmazás indításakor lefut.
     *
     * <p>A metódus ellenőrzi, hogy az "admin" és a "testuser" fiókok léteznek-e, és ha nem, létrehozza azokat.</p>
     *
     * @return A {@code CommandLineRunner} objektum, ami elvégzi az inicializálást.
     */
    @Bean
    public CommandLineRunner initData() {
        return args -> {

            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(UserEntity.builder()
                        .username("admin")
                        .email("admin@ggames.com")
                        .password("$2a$10$gvi14Z5VcFRBRwos7b5FiuHpauKhmirdwt0o4HtPtu3VrnTNNIqd.")
                        .userRole("ADMIN")
                        .build());
                System.out.println("Admin user created!");
            }

            if (!userRepository.existsByUsername("testuser")) {
                userRepository.save(UserEntity.builder()
                        .username("user")
                        .email("user@ggames.com")
                        .password("$2a$10$LqitC4qE6VVY/Da46mS6rOKBC9ztNpR13WHWJv4CupWafFrcUplhe")
                        .userRole("USER")
                        .build());
                System.out.println("User user created!");
            }
        };
    }
}