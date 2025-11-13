package com.ggames.GGames.Service;

import com.ggames.GGames.Service.Dto.FriendDto;
import com.ggames.GGames.Service.Dto.FriendRequestDto;
import com.ggames.GGames.Service.Dto.UserSuggestDto;
import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.UserEntity;

import java.util.List;

/**
 * Interfész a baráti kapcsolatokkal kapcsolatos üzleti logikát kezelő szolgáltatáshoz.
 *
 * <p>Felelős a barátkérések küldéséért, fogadásáért, kezeléséért, valamint a barátlista lekérdezéséért.</p>
 */
public interface FriendshipService {

    /**
     * Barátkérést küld a megadott címzett felhasználó felé.
     *
     * @param sender A kérést küldő felhasználó entitása.
     * @param receiverId A cél felhasználó azonosítója.
     * @return A létrehozott {@code FriendshipEntity} objektum.
     * @throws FriendshipException Ha a kérés nem küldhető el (pl. már barátok, vagy a felhasználó nem létezik).
     */
    FriendshipEntity sendFriendRequest(UserEntity sender, Long receiverId) throws FriendshipException;

    /**
     * Lekéri az összes függőben lévő barátkérést, amely az adott felhasználóhoz érkezett.
     *
     * @param receiver A címzett felhasználó entitása.
     * @return {@code FriendRequestDto} objektumok listája, a függőben lévő kéréseket reprezentálva.
     */
    List<FriendRequestDto> getPendingRequests(UserEntity receiver);

    /**
     * Elfogadja a megadott barátkérést, és aktív barátsági státuszra állítja.
     *
     * @param receiver Az aktuálisan bejelentkezett felhasználó (aki elfogadja a kérést).
     * @param requestId Az elfogadandó barátkérés azonosítója.
     * @return A mentett {@code FriendshipEntity} objektum.
     * @throws FriendshipException Ha a kérés nem található, vagy a jogosultság/állapot nem megfelelő.
     */
    FriendshipEntity acceptFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException;

    /**
     * Elutasítja és törli a megadott barátkérést.
     *
     * @param receiver Az aktuálisan bejelentkezett felhasználó (aki elutasítja a kérést).
     * @param requestId Az elutasítandó barátkérés azonosítója.
     * @throws FriendshipException Ha a kérés nem található, vagy nincs jogosultság a kérelemhez.
     */
    void rejectFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException;

    /**
     * Lekéri azon felhasználók listáját, akiket barátként javasolni lehet az aktuális felhasználónak.
     *
     * @param currentUser Az aktuálisan bejelentkezett felhasználó entitása.
     * @return {@code UserSuggestDto} objektumok listája.
     */
    List<UserSuggestDto> getSuggestableUsers(UserEntity currentUser);

    /**
     * Lekéri az aktuális felhasználó összes elfogadott barátjának listáját.
     *
     * @param currentUser Az aktuálisan bejelentkezett felhasználó.
     * @return {@code FriendDto} objektumok listája, a felhasználó barátait reprezentálva.
     */
    List<FriendDto> getFriends(UserEntity currentUser);

    /**
     * Megszámolja, hány függőben lévő barátkérés érkezett az adott felhasználóhoz.
     *
     * @param receiver A címzett felhasználó entitása.
     * @return A függőben lévő kérések száma.
     */
    int countPendingRequests(UserEntity receiver);

    /**
     * Ellenőrzi, hogy létezik-e aktív (elfogadott) barátsági kapcsolat két felhasználó között.
     *
     * @param userId1 Az első felhasználó azonosítója.
     * @param userId2 A második felhasználó azonosítója.
     * @return {@code true}, ha a felek barátok; egyébként {@code false}.
     */
    boolean areFriends(Long userId1, Long userId2);
}