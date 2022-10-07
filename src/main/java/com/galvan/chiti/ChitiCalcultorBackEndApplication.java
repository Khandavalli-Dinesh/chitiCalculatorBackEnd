package com.galvan.chiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChitiCalcultorBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChitiCalcultorBackEndApplication.class, args);
		System.out.println("swagger is avilable at url: http://localhost:8080/swagger-ui/index.html#/");
	}

}
