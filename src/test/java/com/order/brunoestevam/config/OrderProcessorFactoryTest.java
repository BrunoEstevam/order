package com.order.brunoestevam.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.exception.InvalidDataException;
import com.order.brunoestevam.service.OrderProcessorService;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorFactoryTest {
	
	@InjectMocks
	private OrderProcessorFactory orderProcessorFactory;

	@Mock
	private OrderProcessorService processor1;

	@Mock
	private OrderProcessorService processor2;

	@BeforeEach
	public void setUp() {
		BDDMockito.lenient().when(processor1.isStatus("cre")).thenReturn(true);
		BDDMockito.lenient().when(processor2.isStatus("pen")).thenReturn(true);
		orderProcessorFactory = new OrderProcessorFactory(List.of(processor1, processor2));
	}

	@Test
	@DisplayName("Deve retornar processador quando o status bater")
	public void shouldReturnProcessorWhenStatusMatches() {
		OrderProcessorService result = orderProcessorFactory.getProcessor("cre");
		assertEquals(processor1, result);
	}

	@Test
	@DisplayName("Deve lançar exceção quando o status não bater")
	public void shouldThrowExceptionWhenNoProcessorMatch() {
		InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
			orderProcessorFactory.getProcessor("TESTE");
		});
		assertEquals("No processor available for status: TESTE", exception.getMessage());
	}
}
