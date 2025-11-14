package com.ggames.GGames.Service.Dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Adatátviteli objektum (Data Transfer Object) a játékadatok kezeléséhez.
 *
 * <p>Ezt az osztályt használja a Service és a Controller réteg az adatok továbbítására és a
 * validációra, valamint az űrlapok (form) és listák megjelenítéséhez. A Lombok {@code @Data}
 * annotációja biztosítja az alapvető getter/setter metódusokat.</p>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDto {
    /**
     * A játék egyedi azonosítója.
     */
    private Long id;
    /**
     * A játék képfájljának neve vagy elérési útja.
     */
    private String image;
    /**
     * A játék neve.
     */
    private String name;
    /**
     * A játék fejlesztője.
     */
    private String developer;
    /**
     * A játék kiadója.
     */
    private String publisher;

    /**
     * A játék megjelenési dátuma.
     * Dátum formátum: éééé-hh-nn.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date relaseDate;
    /**
     * A játék letöltési linkje.
     */
    private String downloadLink;
    /**
     * A játék online játszási linkje.
     */
    private String playLink;
    /**
     * Címkék vagy kategóriák a kereséshez/szűréshez.
     */
    private String tags;
    /**
     * A játék rövid leírása.
     */
    private String description;
    /**
     * A játék ára.
     */
    private BigDecimal price;

    /**
     * Részleges konstruktor a játék legfontosabb adatainak inicializálásához.
     *
     * @param name A játék neve.
     * @param description A játék leírása.
     * @param price A játék ára.
     * @param developer A fejlesztő neve.
     * @param publisher A kiadó neve.
     */
    public GameDto(String name, String description, BigDecimal price, String developer, String publisher) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.developer = developer;
        this.publisher = publisher;
    }
}