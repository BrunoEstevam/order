package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.order.brunoestevam.exception.InvalidDataException;
import com.order.brunoestevam.service.impl.IdempotenteServiceImpl;

@ExtendWith(value = MockitoExtension.class)
public class IdempotenteServiceImplTest {
	
	@InjectMocks
	private IdempotenteServiceImpl idempotenteService;

	@Mock
	private StringRedisTemplate redisTemplate;

	@Test
	public void shouldThrowExceptionWhenKeyExists() {
		String key = "teste-key";
		when(redisTemplate.hasKey(key)).thenReturn(true);

		assertThrows(InvalidDataException.class, () -> idempotenteService.validate(key));
	}

	@Test
	public void shouldNotThrowExceptionWhenKeyDoesNotExist() {
		String key = "teste-key";
		when(redisTemplate.hasKey(key)).thenReturn(false);

		assertDoesNotThrow(() -> idempotenteService.validate(key));
	}
}
