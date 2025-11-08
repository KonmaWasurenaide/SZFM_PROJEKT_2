package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entitás a játékadatbázis egy bejegyzésének reprezentálására.
 *
 * <p>Ez az osztály tárolja a játékok alapvető információit, linkjeit és árait.</p>
 */
@Entity
@Table(name = "Games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     */
    @Column(name = "relase_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date relaseDate;

    /**
     * A játék letöltési linkje (pl. bináris fájl).
     */
    @Column(name = "download_link")
    private String downloadLink;

    /**
     * A játék online játszási linkje (pl. Unity WebGL link).
     */
    @Column(name = "play_link")
    private String playLink;

    /**
     * Címkék vagy kategóriák a kereséshez/szűréshez.
     */
    @Column(name = "tags")
    private String tags;

    /**
     * A játék rövid leírása.
     */
    @Column(name = "description")
    private String description;

    /**
     * A játék ára.
     */
    @Column(name = "price")
    private BigDecimal price;
}