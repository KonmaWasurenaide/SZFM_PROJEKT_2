package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entitás a játékadatbázis egy bejegyzésének reprezentálására.
 *
 * <p>Ez az osztály tárolja a játékok alapvető információit, linkjeit és árait.
 * Használja a Lombok annotációkat a boilerplate kód minimalizálására.</p>
 */
@Entity
@Table(name = "Games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {

    /**
     * A játék egyedi azonosítója (elsődleges kulcs).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * A játék képfájljának neve vagy elérési útja.
     */
    @Column(name = "image")
    private String image;

    /**
     * A játék neve (kötelező mező).
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * A játék fejlesztője.
     */
    @Column(name = "developer")
    private String developer;

    /**
     * A játék kiadója.
     */
    @Column(name = "publisher")
    private String publisher;

    /**
     * A játék megjelenési dátuma.
     * Az adatformátum: éééé-hh-nn.
     */
    @Column(name = "relase_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date relaseDate;

    /**
     * A játék letöltési linkje (pl. bináris fájlra mutató URL).
     */
    @Column(name = "download_link")
    private String downloadLink;

    /**
     * A játék online játszási linkje (pl. Unity WebGL vagy más beágyazható link).
     */
    @Column(name = "play_link")
    private String playLink;

    /**
     * Címkék vagy kategóriák a kereséshez/szűréshez (például vesszővel elválasztva).
     */
    @Column(name = "tags")
    private String tags;

    /**
     * A játék rövid leírása.
     */
    @Column(name = "description")
    private String description;

    /**
     * A játék ára, {@code BigDecimal} típussal a pontos pénzügyi számításokhoz.
     */
    @Column(name = "price")
    private BigDecimal price;
}