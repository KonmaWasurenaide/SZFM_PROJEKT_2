package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

/**
 * A(z) {@code GameEntity} osztály a játékadatbázis egy bejegyzését reprezentálja.
 * <p>
 * Ez egy JPA entitásként szolgál, a(z) "Games" adatbázistáblához van hozzárendelve.
 * Tartalmazza egy játék alapvető adatait, mint például a nevét,
 * fejlesztőjét, kiadóját és megjelenési dátumát.
 * </p>
 */
@Entity
@Table(name = "Games")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {
    /**
     * A játék egyedi azonosítója.
     * Ez az elsődleges kulcs (Primary Key) és automatikusan generálódik.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    /**
     * A játék neve.
     * Az adatbázisban nem lehet null értékű.
     */
    @Column(name = "name",nullable = false)
    private String name;

    /**
     * A játék fejlesztőjének (developer) neve.
     */
    @Column(name = "developer")
    private String developer;

    /**
     * A játék kiadójának (publisher) neve.
     */
    @Column(name = "publisher")
    private String publisher;

    /**
     * A játék megjelenési dátuma.
     */
    @Column(name = "relase_date")
    private Date relaseDate;

    /**
     * A játékhoz kapcsolódó címkék vagy műfajok (tags),
     * általában valamilyen elválasztóval ellátott string formájában.
     */
    @Column(name = "tags")
    private String tags;

    /**
     * A játék részletes leírása.
     */
    @Column(name = "description")
    private String description;

}