package com.glofox.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GlofoxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlofoxApiApplication.class, args);
    }

}
