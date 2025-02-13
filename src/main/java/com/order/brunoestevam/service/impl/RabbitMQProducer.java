package com.order.brunoestevam.service.impl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.order.brunoestevam.repository.OrderEntity;

@Service
public class RabbitMQProducer {

	private final RabbitTemplate rabbitTemplate;
	private final Gson gson;

	public RabbitMQProducer(RabbitTemplate rabbitTemplate, Gson gson) {
		this.rabbitTemplate = rabbitTemplate;
		this.gson = gson;
	}

	public void sendOrderUpdate(OrderEntity order) {
		MessageProperties properties = new MessageProperties();
		properties.setContentType("application/json");

		Message message = new Message(gson.toJson(order).getBytes(), properties);
		rabbitTemplate.send("order.v1.status-change", message);
	}
}
