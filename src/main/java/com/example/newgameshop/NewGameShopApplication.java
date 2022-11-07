package com.example.newgameshop;

import com.github.pagehelper.PageInterceptor;
import io.minio.MinioClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Properties;

@SpringBootApplication
@MapperScan(basePackages = "com.example.newgameshop.mapper")
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
    @Bean
    public PageInterceptor pageHelper() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("helperDialect", "mysql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
        //aaa
    }
}
