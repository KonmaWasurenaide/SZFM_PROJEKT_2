package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {

    @Query(
            value = "SELECT * FROM users WHERE id = :id",
            nativeQuery = true
    )
    Optional<UserEntity> findById(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM users WHERE username = :username",
            nativeQuery = true
    )
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query(
            value = "SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)",
            nativeQuery = true
    )
    boolean existsByUsername(@Param("username") String username);

    @Query(
            value = "SELECT * FROM users WHERE email = :email",
            nativeQuery = true
    )
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query(
            value = "SELECT * FROM users",
            nativeQuery = true
    )
    List<UserEntity> findAll();


    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO users (username, password, email, role) VALUES (:username, :password, :email, :role)",
            nativeQuery = true
    )
    void insertUser(
            @Param("username") String username,
            @Param("password") String password,
            @Param("email") String email,
            @Param("role")String role
    );

    UserEntity save(UserEntity user);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE users SET username =:username, email = :email, role = :role WHERE id = :id",
            nativeQuery = true
    )
    int updateEmailAndRole(
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") String role,
            @Param("id") Long id
    );

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM users WHERE id = :id",
            nativeQuery = true
    )
    void deleteById(@Param("id") Long id);
}