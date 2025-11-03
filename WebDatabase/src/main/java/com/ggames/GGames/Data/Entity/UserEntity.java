package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * A(z) {@code UserEntity} osztály egy felhasználói fiókot reprezentál
 * az adatbázisban.
 * <p>
 * Ez egy JPA entitás, a(z) "users" adatbázistáblához van hozzárendelve,
 * és tartalmazza a felhasználó hitelesítési és alapvető azonosító adatait,
 * mint például a felhasználónév, e-mail cím és jelszó.
 * </p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    /**
     * A felhasználói entitás egyedi azonosítója (Primary Key).
     * Ez az ID automatikusan generálódik.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A felhasználó egyedi felhasználóneve.
     * Nem lehet null értékű és egyedinek (unique) kell lennie.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * A felhasználó egyedi e-mail címe.
     * Nem lehet null értékű és egyedinek (unique) kell lennie.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * A felhasználó titkosított jelszava.
     * Nem lehet null értékű.
     */
    @Column(nullable = false)
    private String password;

    /**
     * A felhasználó jogosultsági szerepköre.
     * Alapértelmezett értéke: "USER".
     */
    @Column(name = "role")
    private String role = "USER";

    /**
     * Konstruktor a felhasználónév és jelszó inicializálásához.
     * <p>
     * Ezt tipikusan a bejelentkezési adatokból történő gyors
     * entitás létrehozásához használják.
     * </p>
     *
     * @param username A felhasználó felhasználóneve.
     * @param password A felhasználó jelszava.
     */
    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}