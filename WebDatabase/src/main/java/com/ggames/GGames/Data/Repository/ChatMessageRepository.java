package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interfész a {@link ChatMessageEntity} entitások adatbázis-műveleteinek kezelésére.
 *
 * <p>Kiterjeszti a {@code JpaRepository}-t, amely alapvető CRUD műveleteket biztosít.</p>
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    /**
     * Lekéri a két felhasználó közötti összes üzenetet (beszélgetés előzményeit).
     *
     * <p>A lekérdezés a {@code sender.id} és {@code receiver.id} mezők alapján működik,
     * függetlenül attól, hogy melyik felhasználó küldte vagy fogadta az üzenetet.
     * Az eredményt időbélyeg ({@code timestamp}) alapján növekvő sorrendben (időrendben) adja vissza.</p>
     *
     * @param userId1 Az első felhasználó azonosítója.
     * @param userId2 A második felhasználó azonosítója.
     * @return {@code ChatMessageEntity} objektumok listája, amely tartalmazza a beszélgetés teljes történetét.
     */
    @Query("SELECT m FROM ChatMessageEntity m " +
            "WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) " +
            "OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) " +
            "ORDER BY m.timestamp ASC")
    List<ChatMessageEntity> findConversationHistory(Long userId1, Long userId2);
}