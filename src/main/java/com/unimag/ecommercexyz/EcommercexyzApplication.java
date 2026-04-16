package com.unimag.ecommercexyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class EcommercexyzApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercexyzApplication.class, args);
	}

}
