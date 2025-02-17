package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.order.brunoestevam.exception.InvalidDataException;
import com.order.brunoestevam.service.impl.IdempotenteService;

@ExtendWith(value = MockitoExtension.class)
public class IdempotenteServiceImplTest {
	
	@InjectMocks
	private IdempotenteService idempotenteService;

	@Mock
	private StringRedisTemplate redisTemplate;

	@Test
	@DisplayName("Deve lançar exceção quando a chave existir")
	public void shouldThrowExceptionWhenKeyExists() {
		String key = "dsadsa";
		when(redisTemplate.hasKey(key)).thenReturn(true);

		assertThrows(InvalidDataException.class, () -> idempotenteService.validateAndPut(key));
	}
}
