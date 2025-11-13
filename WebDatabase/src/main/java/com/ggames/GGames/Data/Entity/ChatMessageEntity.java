package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entitás osztály a csevegő üzenetek adatbázisban történő tárolásához.
 *
 * <p>Minden példány egy privát csevegő üzenetet reprezentál két felhasználó között.</p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
public class ChatMessageEntity {

    /**
     * Az üzenet egyedi azonosítója (elsődleges kulcs).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Az üzenetet küldő felhasználó entitása.
     * Nem lehet null értékű.
     */
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    /**
     * Az üzenet címzettje (fogadó) felhasználó entitása.
     * Nem lehet null értékű.
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    /**
     * Az üzenet tényleges szöveges tartalma.
     * Nagyobb szövegek tárolására alkalmas (LOB - Large Object). Nem lehet null értékű.
     */
    @Lob
    @Column(nullable = false,name = "content")
    private String content;

    /**
     * Az üzenet elküldésének időbélyege.
     * Nem lehet null értékű.
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime timestamp;
}