package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entitás a felhasználók közötti baráti kapcsolatok (barátkérések) tárolására.
 *
 * <p>A kapcsolat állapotát (például FÜGGŐBEN, ELFOGADVA, ELUTASÍTVA) tárolja a {@code FriendshipStatus} enumeráció segítségével.</p>
 */
@Entity
@Table(name = "friendships")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipEntity {

    /**
     * A baráti kapcsolat rekordjának egyedi azonosítója (elsődleges kulcs).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * A barátkérést küldő felhasználó entitása.
     * Kapcsolat: Több kérés egy küldőhöz (Many-to-One). Lusta betöltést (Lazy) használ a teljesítmény érdekében.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    /**
     * A barátkérés címzettje (fogadó) felhasználó entitása.
     * Kapcsolat: Több kérés egy fogadóhoz (Many-to-One). Lusta betöltést (Lazy) használ a teljesítmény érdekében.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    /**
     * A baráti kapcsolat aktuális állapota (pl. PENDING, ACCEPTED, REJECTED).
     * Stringként tárolódik az adatbázisban.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FriendshipStatus status;

    /**
     * A baráti kérés létrehozásának időbélyege.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * {@code PrePersist} callback metódus.
     *
     * <p>A perzisztálás előtt automatikusan beállítja a {@code createdAt} mező értékét az aktuális időre.</p>
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}