package com.example.newgameshop;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class NewGameShopApplication {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    public static void main(String[] args) {
        SpringApplication.run(NewGameShopApplication.class, args);
    }
    @Bean
    public CorsConfiguration getCorsConfiguration(){
      CorsConfiguration cors=new CorsConfiguration();
      cors.addAllowedOrigin("http://localhost:80");
      cors.addAllowedHeader("*");
      cors.addAllowedMethod("*");
      cors.setAllowCredentials(true);
      return cors;
    }
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
    }
}
