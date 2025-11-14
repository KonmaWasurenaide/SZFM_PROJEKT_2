package com.ggames.GGames.Service.Dto;

import com.ggames.GGames.Data.Entity.FriendshipEntity;
import lombok.Value;

/**
 * Változtathatatlan adatátviteli objektum (DTO) egy barátkérés részletes adatainak szállítására.
 *
 * <p>A {@code @Value} annotáció miatt ez a DTO a kérés azonosítóját, a küldő felhasználó adatait
 * és a kérés aktuális állapotát foglalja magába. Ideális küldő felhasználói kérések megjelenítéséhez.</p>
 */
@Value
public class FriendRequestDto {
    /**
     * A barátkérés rekordjának egyedi azonosítója.
     */
    private Long requestId;
    /**
     * A barátkérést küldő felhasználó egyedi azonosítója.
     */
    private Long senderId;
    /**
     * A barátkérést küldő felhasználó bejelentkezési neve.
     */
    private String senderUsername;
    /**
     * A barátsági kapcsolat állapota (pl. PENDING, ACCEPTED) szöveges formában.
     */
    private String status;


    /**
     * Statikus gyári metódus, amely egy {@code FriendshipEntity} entitásból létrehoz egy {@code FriendRequestDto} példányt.
     *
     * <p>A metódus kinyeri az azonosítókat, a küldő felhasználó nevét, és a kérés állapotát.</p>
     *
     * @param friendship A konvertálandó {@code FriendshipEntity} objektum.
     * @return Az új, feltöltött {@code FriendRequestDto} példány.
     */
    public static FriendRequestDto fromEntity(FriendshipEntity friendship) {
        return new FriendRequestDto(
                friendship.getId(),
                friendship.getSender().getId(),
                friendship.getSender().getUsername(),
                friendship.getStatus().name()
        );
    }
}