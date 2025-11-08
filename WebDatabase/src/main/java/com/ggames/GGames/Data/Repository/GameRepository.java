package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interfész a {@code GameEntity} adatok eléréséhez.
 *
 * <p>Kiterjeszti a {@code JpaRepository}-t, amely alapvető CRUD műveleteket biztosít.
 * Emellett egyedi keresési metódusokat is tartalmaz a játék neve, fejlesztője, kiadója és címkéi alapján.</p>
 */
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    /**
     * Megkeres egy játékot az azonosítója (ID) alapján.
     *
     * @param id A keresett játék azonosítója.
     * @return Az {@code Optional} tartalmazza a játékot, ha megtalálható.
     */
    @Override
    Optional<GameEntity> findById(Long id);

    /**
     * Megkeres egy játékot a pontos neve alapján.
     *
     * @param name A keresett játék neve.
     * @return Az {@code Optional} tartalmazza a játékot, ha megtalálható.
     */
    Optional<GameEntity> findByName(String name);
}