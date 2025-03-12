package com.example.itau;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Desafio", version = "1"))
public class JavaSpringDesafioItauApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringDesafioItauApplication.class, args);
	}

}
