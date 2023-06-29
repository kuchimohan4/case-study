package com.cropdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableEurekaServer
@SpringBootApplication
@CrossOrigin("http://localhost:4200")
public class CropdealDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CropdealDiscoveryServerApplication.class, args);
	}

}
