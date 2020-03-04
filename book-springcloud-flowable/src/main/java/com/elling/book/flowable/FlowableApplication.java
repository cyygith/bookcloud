package com.elling.book.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FlowableApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlowableApplication.class, args);
	}
}
