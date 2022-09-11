package com.example.externalpost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.example.trackingpostcore"}) //com.example.trackingpostcore 하위에 있는 jpaRepository를 상속한 repository scan
@EntityScan(basePackages = {"com.example.trackingpostcore"}) //com.example.trackingpostcore 하위에 있는 @Entity 클래스 scan
public class ExternalpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalpostApplication.class, args);
	}

}
