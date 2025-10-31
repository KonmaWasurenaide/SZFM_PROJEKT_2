package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity , Long> {

    Optional<GameEntity> findByName(String name);
    Optional<GameEntity> findByid(Long id);



}
