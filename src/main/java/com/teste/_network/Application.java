package com.teste._network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Teste 4NetWork", version = "1", description = "API Teste de Desenvolvimento de uma API utilizando a linguagem Java."))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
