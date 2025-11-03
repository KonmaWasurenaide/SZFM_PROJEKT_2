package com.ggames.GGames;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GGamesApplication {

	public static void main(String[] args) {
        SpringApplication.run(GGamesApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper m = new ModelMapper();
        return m;}
}
