package com.majesty.gerenciador;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Gerenciador de projeto REST API",
				description = "API para gerenciamento de projetos da empresa, incluindo status, alocação de membros e relatórios.",
				version = "v1.0",
				contact = @Contact(
						name = "Jacob",
						url = "https://github.com/jacob-majesty/gerenciador-projeto"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Documentação completa da API",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
public class GerenciadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorApplication.class, args);
	}

}
