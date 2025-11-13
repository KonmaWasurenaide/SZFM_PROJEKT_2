package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.FriendshipStatus;
import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interfész a {@link FriendshipEntity} entitások adatbázis-műveleteinek kezelésére.
 *
 * <p>Lehetővé teszi a baráti kapcsolatok keresését, számlálását és a barátnak nem tekinthető felhasználók lekérdezését is.</p>
 */
public interface FriendshipRepository extends JpaRepository<FriendshipEntity, Long> {

    /**
     * Megkeresi a két felhasználó közötti barátság rekordot (függetlenül attól, hogy ki volt a küldő és ki a fogadó).
     *
     * <p>A metódus kéri a {@code (user1, user2)} és {@code (user3, user4)} párokat a felcserélt kereséshez.
     * Általában {@code findBySenderAndReceiverOrReceiverAndSender(A, B, B, A)} formában használatos.</p>
     *
     * @param user1 Az egyik potenciális küldő.
     * @param user2 Az egyik potenciális fogadó.
     * @param user3 A másik potenciális küldő.
     * @param user4 A másik potenciális fogadó.
     * @return Az opcionális barátság entitás, ha létezik a kapcsolat.
     */
    Optional<FriendshipEntity> findBySenderAndReceiverOrReceiverAndSender(
            UserEntity user1, UserEntity user2, UserEntity user3, UserEntity user4);

    /**
     * Visszaadja az összes barátság rekordot, ahol az adott felhasználó a címzett és a kapcsolat állapota megegyezik a megadott állapottal.
     *
     * <p>Ezt használják például a függőben lévő kérések (PENDING) lekérésére az adott címzett számára.</p>
     *
     * @param receiver A címzett felhasználó entitása.
     * @param status A keresendő barátsági állapot.
     * @return A megfelelő {@code FriendshipEntity} objektumok listája.
     */
    List<FriendshipEntity> findByReceiverAndStatus(UserEntity receiver, FriendshipStatus status);

    /**
     * Visszaadja az összes barátság rekordot, ahol az adott felhasználó a kérés küldője volt.
     *
     * @param sender A küldő felhasználó entitása.
     * @return A megfelelő {@code FriendshipEntity} objektumok listája.
     */
    List<FriendshipEntity> findBySender(UserEntity sender);

    /**
     * Visszaadja az összes barátság rekordot, ahol az adott felhasználó a kérés címzettje volt.
     *
     * @param receiver A címzett felhasználó entitása.
     * @return A megfelelő {@code FriendshipEntity} objektumok listája.
     */
    List<FriendshipEntity> findByReceiver(UserEntity receiver);

    /**
     * Visszaadja az összes barátság rekordot, ahol az adott felhasználó a küldő, és a kapcsolat állapota megegyezik a megadott állapottal.
     *
     * @param sender A küldő felhasználó entitása.
     * @param status A keresendő barátsági állapot.
     * @return A megfelelő {@code FriendshipEntity} objektumok listája.
     */
    List<FriendshipEntity> findBySenderAndStatus(UserEntity sender, FriendshipStatus status);

    /**
     * Lekéri azoknak a felhasználóknak a listáját, akikkel az aktuális felhasználó nem barát, és nincs függőben lévő kérése.
     *
     * <p>A lekérdezés kizárja a jelenlegi felhasználót, valamint azokat a felhasználókat, akikkel már aktív (ACCEPTED) vagy függőben lévő (PENDING) kapcsolata van.</p>
     *
     * @param currentUserId Az aktuálisan bejelentkezett felhasználó azonosítója.
     * @return A javasolt felhasználók {@code UserEntity} objektumainak listája.
     */
    @Query("SELECT u FROM UserEntity u WHERE u.id != :currentUserId AND u.id NOT IN (" +
            "    SELECT CASE WHEN f.sender.id = :currentUserId THEN f.receiver.id ELSE f.sender.id END " +
            "    FROM FriendshipEntity f " +
            "    WHERE (f.sender.id = :currentUserId OR f.receiver.id = :currentUserId) " +
            "    AND f.status IN ('ACCEPTED', 'PENDING')" +
            ")")
    List<UserEntity> findSuggestableUsers(@Param("currentUserId") Long currentUserId);

    /**
     * Megszámolja az összes olyan barátság rekordot, ahol az adott felhasználó a címzett, és a kapcsolat állapota megegyezik a megadott állapottal.
     *
     * <p>Ezt a függőben lévő barátkérések (PENDING) számolására használják.</p>
     *
     * @param receiver A címzett felhasználó entitása.
     * @param status A keresendő barátsági állapot.
     * @return Az állapotnak megfelelő rekordok száma.
     */
    int countByReceiverAndStatus(UserEntity receiver, FriendshipStatus status);

    /**
     * Ellenőrzi, hogy létezik-e aktív (ACCEPTED) barátsági kapcsolat a két megadott felhasználó között.
     *
     * <p>A keresést mindkét irányban elvégzi (userId1 küldője userId2-nek, vagy fordítva), és csak az {@code ACCEPTED} állapotot veszi figyelembe.</p>
     *
     * @param userId1 Az első felhasználó azonosítója.
     * @param userId2 A második felhasználó azonosítója.
     * @return {@code true}, ha a két felhasználó barát; egyébként {@code false}.
     */
    @Query("SELECT COUNT(f) > 0 FROM FriendshipEntity f " +
            "WHERE (f.sender.id = :userId1 AND f.receiver.id = :userId2 AND f.status = 'ACCEPTED') " +
            "OR (f.sender.id = :userId2 AND f.receiver.id = :userId1 AND f.status = 'ACCEPTED')")
    boolean existsActiveFriendship(Long userId1, Long userId2);
}