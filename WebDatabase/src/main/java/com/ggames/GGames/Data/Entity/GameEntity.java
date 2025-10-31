package com.ggames.GGames.Data.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "Games")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "developer")
    private String developer;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "relase_date")
    private Date relaseDate;

    @Column(name = "tags")
    private String tags;

    @Column(name = "description")
    private String description;

}
