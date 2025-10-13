package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
