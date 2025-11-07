package com.ggames.GGames.Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * Adatátviteli objektum (Data Transfer Object) a felhasználói adatok kezeléséhez.
 *
 * <p>Ezt az objektumot használja a Controller a regisztrációs űrlap adatainak lekötésére,
 * és az adatok Service réteg felé történő továbbítására.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;

    /**
     * Konstruktor a felhasználói adatok alapvető mezőinek inicializálásához.
     *
     * @param username A felhasználónév.
     * @param email Az email cím.
     * @param password A jelszó (többnyire még hash-elés előtti).
     * @param role A felhasználó szerepköre.
     */
    public UserDto(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}