package com.order.brunoestevam.service.impl;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.mapper.OrderMapper;

@Service
public class RabbitMQConsumer {
	private final OrderService orderService;
	
	public RabbitMQConsumer(OrderService orderService) {
		this.orderService = orderService;
	}

	@RabbitListener(queues = "order.v1.processor")
	public void receiveMessage(ProcessOrderRequest request, @Headers Map<String, Object> headers) {
		orderService.process(OrderMapper.INSTANCE.mapToEntity(request), headers.get("Idempotency-Key").toString());
	}
}
