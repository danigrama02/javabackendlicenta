package com.licenta.licenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication()
public class LicentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicentaApplication.class, args);
    }

}
