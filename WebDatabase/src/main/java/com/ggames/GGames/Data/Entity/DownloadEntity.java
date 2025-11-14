package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entitás a felhasználói könyvtárban lévő játékok (letöltött tartalmak) tárolására.
 *
 * <p>Kapcsolótáblaként funkcionál, összekapcsolva a {@code UserEntity}-t és a {@code GameEntity}-t,
 * ezzel reprezentálva, hogy egy adott felhasználó mely játékokat birtokolja.</p>
 */
@Entity
@Table(name = "downloaded")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DownloadEntity {

    /**
     * A letöltés rekordjának egyedi azonosítója (elsődleges kulcs).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Hivatkozás a játékot letöltő felhasználóra.
     * Kapcsolat: Több letöltés egy felhasználóhoz (Many-to-One). Nem lehet null értékű.
     */
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Hivatkozás a felhasználó által birtokolt játékra.
     * Kapcsolat: Több letöltés egy játékhoz (Many-to-One). Nem lehet null értékű.
     */
    @ManyToOne()
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    /**
     * A játék könyvtárhoz adásának/letöltésének dátuma.
     */
    @Column(name = "download_date")
    private LocalDate download_date;
}