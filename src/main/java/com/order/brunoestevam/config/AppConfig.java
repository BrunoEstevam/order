package com.order.brunoestevam.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.google.gson.Gson;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableJpaAuditing
@EnableCaching
public class AppConfig {

	@Bean
	Gson gson() {
		return new Gson();
	}

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Order").version("1.0")
				.license(new License().name("BrunoEstevam")));
	}
}
