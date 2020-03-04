package com.elling.book.eurekaserverbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverBookApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaserverBookApplication.class, args);
	}
}
