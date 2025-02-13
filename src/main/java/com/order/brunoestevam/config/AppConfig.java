package com.order.brunoestevam.config;

import java.awt.print.Book;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.gson.Gson;

@Configuration
@EnableJpaAuditing
@EnableCaching
public class AppConfig {

	@Bean
	Gson gson() {
		return new Gson();
	}
	
	@Bean
	RedisTemplate<Long, Book> redisTemplate(RedisConnectionFactory connectionFactory) {
	    RedisTemplate<Long, Book> template = new RedisTemplate<>();
	    template.setConnectionFactory(connectionFactory);
	    
	    return template;
	}
}
