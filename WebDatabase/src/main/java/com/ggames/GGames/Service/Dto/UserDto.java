package com.ggames.GGames.Service.Dto;

import lombok.*;

/**
 * A(z) {@code UserDto} osztály egy Data Transfer Object (DTO), amely
 * a felhasználói adatok (főleg a regisztráció/hitelesítés és a megjelenítés)
 * továbbítására szolgál a szolgáltatási réteg és a külső rétegek között.
 * <p>
 * Csak az adatmezőket tartalmazza, Lombok annotációkkal támogatva
 * a boilerplate kód minimalizálását.
 * </p>
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * A felhasználó felhasználóneve.
     */
    private String username;

    /**
     * A felhasználó e-mail címe.
     */
    private String email;

    /**
     * A felhasználó jelszava (csak bejelentkezés/regisztráció esetén továbbítandó).
     */
    private String password;

    /**
     * A felhasználó jogosultsági szerepköre (pl. "USER" vagy "ADMIN").
     */
    private String role;
}