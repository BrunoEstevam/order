package com.order.brunoestevam.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.exception.Error;
import com.order.brunoestevam.service.MessasingProducer;

@Service
public class RabbitMQProducerImpl implements MessasingProducer {

	private final RabbitTemplate rabbitTemplate;
	private final Gson gson;

	public RabbitMQProducerImpl(RabbitTemplate rabbitTemplate, Gson gson) {
		this.rabbitTemplate = rabbitTemplate;
		this.gson = gson;
	}

	@Override
	public void sendOrderUpdate(ProcessOrderResponse response) {
		rabbitTemplate.convertAndSend("order.v1.status-change", gson.toJson(response));
	}

	@Override
	public void sendOrderError(Error error) {
		rabbitTemplate.convertAndSend("order.v1.processor-error", gson.toJson(error));
	}
}
