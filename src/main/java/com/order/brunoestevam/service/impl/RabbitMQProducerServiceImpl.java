package com.order.brunoestevam.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.service.MessasingProducerService;

@Service
public class RabbitMQProducerServiceImpl implements MessasingProducerService {

	private final RabbitTemplate rabbitTemplate;
	private final Gson gson;

	public RabbitMQProducerServiceImpl(RabbitTemplate rabbitTemplate, Gson gson) {
		this.rabbitTemplate = rabbitTemplate;
		this.gson = gson;
	}

	@Override
	public void sendOrderUpdate(OrderResponse response) {
		rabbitTemplate.convertAndSend("order.v1.status-change", gson.toJson(response));
	}

	@Override
	public void sendOrder(OrderMessageRequest request) {
		rabbitTemplate.convertAndSend("order.v1.exchange", "processor", request);
	}
}
