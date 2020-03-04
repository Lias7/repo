package com.czh.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.czh.travel")
public class SpringbootTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTravelApplication.class, args);
    }

}
