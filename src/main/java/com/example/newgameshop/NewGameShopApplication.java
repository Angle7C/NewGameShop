package com.example.newgameshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class NewGameShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewGameShopApplication.class, args);
    }
    @Bean
    public CorsConfiguration getCorsConfiguration(){
        new CorsConfiguration().addAllowedHeader("*");
    }
}
