package com.example.kakaochannel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class) //데이터소스 무시
@SpringBootApplication
public class KakaochannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(KakaochannelApplication.class, args);
    }

}
