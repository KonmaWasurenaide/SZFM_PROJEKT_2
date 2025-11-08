package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interfész a {@code UserEntity} adatok eléréséhez.
 *
 * <p>Kiterjeszti a {@code JpaRepository}-t, alapvető CRUD műveleteket és egyedi keresési metódusokat biztosítva a felhasználónév alapján.</p>
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Megkeres egy felhasználót a felhasználónév alapján.
     *
     * @param username A keresett felhasználónév.
     * @return Az {@code Optional} tartalmazza a {@code UserEntity}-t, ha megtalálható.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Ellenőrzi, hogy létezik-e már felhasználó a megadott felhasználónévvel.
     *
     * @param username Az ellenőrizendő felhasználónév.
     * @return {@code true}, ha a felhasználónév már foglalt; egyébként {@code false}.
     */
    boolean existsByUsername(String username);
}