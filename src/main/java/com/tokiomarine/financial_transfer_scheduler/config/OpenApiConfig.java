package com.tokiomarine.financial_transfer_scheduler.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Agendamento de Transferências API")
						.version("1.0")
						.description("Documentação da API de agendamento de transferências financeiras"))
				.servers(List.of(new Server().url("/api")));
	}
}
