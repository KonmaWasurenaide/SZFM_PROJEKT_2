package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.DownloadEntity;
import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interfész a {@code DownloadEntity} adatok eléréséhez.
 *
 * <p>Kiterjeszti a {@code JpaRepository}-t, alapvető CRUD műveleteket biztosítva,
 * valamint egyedi lekérdezéseket kínál a letöltött játékok adatainak lekérdezésére a felhasználó és a játék kapcsolatán keresztül.</p>
 */
public interface DownloadRepository extends JpaRepository<DownloadEntity, Long> {

    /**
     * Visszaadja az összes letöltési bejegyzést (könyvtári elemet) egy adott felhasználó ID alapján.
     *
     * @param userId A felhasználó egyedi azonosítója.
     * @return A kapcsolódó {@code DownloadEntity} objektumok listája.
     */
    List<DownloadEntity> findByUserId(Long userId);

    /**
     * Visszaadja az összes letöltési bejegyzést (könyvtári elemet) a teljes {@code UserEntity} objektum alapján.
     *
     * @param user A {@code UserEntity} objektum, amelynek a letöltéseit keressük.
     * @return A kapcsolódó {@code DownloadEntity} objektumok listája.
     */
    List<DownloadEntity> findByUser(UserEntity user);

    /**
     * Ellenőrzi, hogy létezik-e már letöltési bejegyzés egy adott felhasználó és egy adott játék között.
     *
     * <p>Ezt a metódust általában annak ellenőrzésére használják, hogy egy felhasználó már birtokolja-e (hozzáadta-e a könyvtárához) egy adott játékot.</p>
     *
     * @param user A {@code UserEntity} objektum.
     * @param game A {@code GameEntity} objektum.
     * @return {@code true}, ha a felhasználó már birtokolja a játékot; egyébként {@code false}.
     */
    boolean existsByUserAndGame(UserEntity user, GameEntity game);
}