package com.order.brunoestevam.service.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.order.brunoestevam.exception.InvalidDataException;

@Service
public class IdempotenteService {

	private final StringRedisTemplate redisTemplate;

	public IdempotenteService(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void validateAndPut(String idempotenteKey) {
		Boolean exists = redisTemplate.hasKey(idempotenteKey);
		if (Boolean.TRUE.equals(exists)) {	
			throw new InvalidDataException("Operação já foi processada.");
		}
		
		put(idempotenteKey);
	}
	
	private void put(String idempotenteKey) {
        redisTemplate.opsForValue().set(idempotenteKey, "processed");
	}
}
