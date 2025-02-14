package com.order.brunoestevam.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.google.gson.Gson;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.exception.Error;
import com.order.brunoestevam.service.impl.RabbitMQProducerImpl;

@ExtendWith(value = MockitoExtension.class)
public class RabbitMQProducerImplTest {

	@InjectMocks
	private RabbitMQProducerImpl rabbitMQProducerImpl;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Mock
	private Gson gson;

	@Test
	public void shouldSendOrderUpdate() {
		ProcessOrderResponse response = new ProcessOrderResponse();
		response.setStatus(OrderStatusEnum.CREATED.getCode());
		String jsonResponse = "{\"id\": 1, \"status\":\"cre\"}"; 

		when(gson.toJson(response)).thenReturn(jsonResponse);

		rabbitMQProducerImpl.sendOrderUpdate(response);

		verify(rabbitTemplate, times(1)).convertAndSend("order.v1.status-change", jsonResponse);
	}
	
	@Test
	public void shouldSendOrderError() {
		Error error = new Error();
		error.setDate(new Date());
		error.setMessages("Erro");
		error.setStatus(0);
		String jsonResponse = "{\"id\": 1, \"status\":\"cre\"}"; 

		when(gson.toJson(error)).thenReturn(jsonResponse);

		rabbitMQProducerImpl.sendOrderError(error);

		verify(rabbitTemplate, times(1)).convertAndSend("order.v1.processor-error", jsonResponse);
	}
}


