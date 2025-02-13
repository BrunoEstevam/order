package com.order.brunoestevam.service.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class IdempotenteService {

	private final StringRedisTemplate redisTemplate;

	public IdempotenteService(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void validate(String idempotenteKey) {
		Boolean exists = redisTemplate.hasKey(idempotenteKey);
		if (Boolean.TRUE.equals(exists)) {	
			throw new IllegalStateException("Operação já foi processada.");
		}
	}
	
	public void put(String idempotenteKey) {
        redisTemplate.opsForValue().set(idempotenteKey, "processed");
	}
}
