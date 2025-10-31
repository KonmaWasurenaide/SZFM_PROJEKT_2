// GameRepository.java
package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.GameEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends Repository<GameEntity, Long> {

    @Query(
            value = "SELECT * FROM games WHERE id = :id",
            nativeQuery = true
    )
    Optional<GameEntity> findGameById(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM games WHERE name = :name",
            nativeQuery = true
    )
    Optional<GameEntity> findGameByName(@Param("name") String name);

    @Query(
            value = "SELECT * FROM games WHERE developer = :developer",
            nativeQuery = true
    )
    List<GameEntity> findGamesByDeveloper(@Param("developer") String developer);

    @Query(
            value = "SELECT * FROM games WHERE publisher = :publisher",
            nativeQuery = true
    )
    List<GameEntity> findGamesByPublisher(@Param("publisher") String publisher);

    @Query(
            value = "SELECT * FROM games WHERE tags LIKE %:tag%",
            nativeQuery = true
    )
    List<GameEntity> findGamesByTag(@Param("tag") String tag);

    @Query(
            value = "SELECT * FROM games",
            nativeQuery = true
    )
    List<GameEntity> findAllGames();

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO games (name, developer, publisher, relase_date, tags, description) " +
                    "VALUES (:name, :developer, :publisher, :relaseDate, :tags, :description)",
            nativeQuery = true
    )
    void insertNewGame(
            @Param("name") String name,
            @Param("developer") String developer,
            @Param("publisher") String publisher,
            @Param("relaseDate") Date relaseDate,
            @Param("tags") String tags,
            @Param("description") String description
    );

    GameEntity save(GameEntity game);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE games SET name = :name, developer = :developer, publisher = :publisher, " +
                    "relase_date = :relaseDate, tags = :tags, description = :description WHERE id = :id",
            nativeQuery = true
    )
    int updateGameDetails(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("developer") String developer,
            @Param("publisher") String publisher,
            @Param("relaseDate") Date relaseDate,
            @Param("tags") String tags,
            @Param("description") String description
    );

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM games WHERE id = :id",
            nativeQuery = true
    )
    void deleteGameById(@Param("id") Long id);
}