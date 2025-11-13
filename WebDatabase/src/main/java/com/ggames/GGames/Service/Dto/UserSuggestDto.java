package com.ggames.GGames.Service.Dto;

import com.ggames.GGames.Data.Entity.UserEntity;
import lombok.Value;

/**
 * Változtathatatlan adatátviteli objektum (DTO) a barátként javasolt felhasználók alapvető adatainak szállítására.
 *
 * <p>Ezt a DTO-t a Service réteg használja annak a felhasználói listának a továbbítására, akikkel az aktuális
 * felhasználónak még nincs kapcsolata (sem barátság, sem függőben lévő kérés).</p>
 */
@Value
public class UserSuggestDto {
    /**
     * A javasolt felhasználó egyedi azonosítója.
     */
    private Long id;
    /**
     * A javasolt felhasználó bejelentkezési neve.
     */
    private String username;

    /**
     * Statikus gyári metódus, amely egy {@code UserEntity} objektumból létrehoz egy {@code UserSuggestDto} példányt.
     *
     * @param user A konvertálandó {@code UserEntity} objektum.
     * @return Az új {@code UserSuggestDto} példány.
     */
    public static UserSuggestDto fromEntity(UserEntity user) {
        return new UserSuggestDto(user.getId(), user.getUsername());
    }
}