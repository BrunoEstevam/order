package com.order.brunoestevam.service.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.order.brunoestevam.exception.InvalidDataException;
import com.order.brunoestevam.service.IdempotenteService;

@Service
public class IdempotenteServiceImpl implements IdempotenteService {

	private final StringRedisTemplate redisTemplate;

	public IdempotenteServiceImpl(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void validate(String idempotenteKey) {
		Boolean exists = redisTemplate.hasKey(idempotenteKey);
		if (Boolean.TRUE.equals(exists)) {	
			throw new InvalidDataException("Operação já foi processada.");
		}
	}
	
	public void put(String idempotenteKey) {
        redisTemplate.opsForValue().set(idempotenteKey, "processed");
	}
}
