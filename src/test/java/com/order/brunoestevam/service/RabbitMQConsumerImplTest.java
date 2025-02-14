package com.order.brunoestevam.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.service.impl.OrderProcessingService;
import com.order.brunoestevam.service.impl.RabbitMQConsumerServiceImpl;

@ExtendWith(value = MockitoExtension.class)
public class RabbitMQConsumerImplTest {

	@InjectMocks
	private RabbitMQConsumerServiceImpl rabbitMQConsumerImpl;

	@Mock
	private OrderProcessingService orderService;

	@Mock
	private MessasingProducerService messasingProducer;

	@Test
	public void shouldReceiveMessageWithSucess() {
		ProcessOrderRequest request = new ProcessOrderRequest();
		Map<String, Object> headers = new HashMap<>();
		headers.put("Idempotency-Key", "idempotente-key");

		when(orderService.process(any(), any())).thenReturn(null); 

		rabbitMQConsumerImpl.receiveMessage(request, headers);

		verify(orderService, times(1)).process(any(), eq("idempotente-key"));
		verify(messasingProducer, never()).sendOrderError(any(com.order.brunoestevam.exception.Error.class));
	}

	@Test
	public void shouldReceiveMessageWithError() {
		ProcessOrderRequest request = new ProcessOrderRequest();
		Map<String, Object> headers = new HashMap<>();
		headers.put("Idempotency-Key", "idempotente-key");

		when(orderService.process(any(), any())).thenThrow(new RuntimeException("Erro de processamento"));

		rabbitMQConsumerImpl.receiveMessage(request, headers);

		verify(orderService, times(1)).process(any(), eq("idempotente-key"));
		verify(messasingProducer, times(1)).sendOrderError(any(com.order.brunoestevam.exception.Error.class));
	}
}
