package com.ggames.GGames.Service.Dto;

import com.ggames.GGames.Data.Entity.UserEntity;
import lombok.Value;

/**
 * Változtathatatlan adatátviteli objektum (DTO) egy barát alapvető adatainak (azonosító és felhasználónév) szállítására.
 *
 * <p>A {@code @Value} annotáció miatt ez a DTO automatikusan tartalmazza az összes mezőt
 * tartalmazó konstruktort, {@code getter} metódusokat, valamint {@code equals()}, {@code hashCode()}
 * és {@code toString()} metódusokat. Ideális választás az adatlekéréshez, mivel nem igényel módosítást.</p>
 */
@Value
public class FriendDto {
    /**
     * A barát egyedi azonosítója.
     */
    private Long id;
    /**
     * A barát felhasználóneve.
     */
    private String username;

    /**
     * Statikus gyári metódus, amely egy {@code UserEntity} objektumból létrehoz egy {@code FriendDto} példányt.
     *
     * <p>Ezt a metódust a Service réteg használja az entitások DTO-vá alakítására, ezzel elrejtve a belső entitás részleteket a Controller réteg elől.</p>
     *
     * @param user A konvertálandó {@code UserEntity} objektum.
     * @return Az új {@code FriendDto} példány.
     */
    public static FriendDto fromEntity(UserEntity user) {
        return new FriendDto(user.getId(), user.getUsername());
    }
}