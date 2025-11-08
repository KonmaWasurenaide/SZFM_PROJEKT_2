package com.ggames.GGames.Service.Dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Adatátviteli objektum (Data Transfer Object) a játékadatok kezeléséhez.
 *
 * <p>Ezt az osztályt használja a Service és a Controller réteg az adatok továbbítására és a
 * validációra, valamint az űrlapok (form) és listák megjelenítéséhez.</p>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDto {
    private Long id;
    private String image;
    private String name;
    private String developer;
    private String publisher;

    /**
     * A játék megjelenési dátuma.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date relaseDate;
    private String downloadLink;
    private String playLink;
    private String tags;
    private String description;
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