package com.ggames.GGames;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * A(z) {@code GGamesApplication} az alkalmazás fő osztálya,
 * amely a Spring Boot alkalmazást elindítja.
 * <p>
 * A {@code @SpringBootApplication} annotáció egyesíti a {@code @Configuration},
 * a {@code @EnableAutoConfiguration} és a {@code @ComponentScan} annotációk funkcióit.
 * </p>
 */
@SpringBootApplication
public class GGamesApplication {

    /**
     * Az alkalmazás fő belépési pontja.
     * <p>
     * A {@link SpringApplication#run} metódus indítja el a Spring Boot alkalmazást,
     * amely beolvassa a konfigurációt és elkezdi a webszerver működtetését.
     * </p>
     *
     * @param args Parancssori argumentumok.
     */
    public static void main(String[] args) {
        SpringApplication.run(GGamesApplication.class, args);
    }

    /**
     * Definiálja a {@link ModelMapper} beant, amely az adatátviteli objektumok (DTO-k)
     * és az entitások közötti egyszerű adatkonverzióra szolgál.
     *
     * @return Egy új {@link ModelMapper} példány.
     */
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper m = new ModelMapper();
        return m;}
}