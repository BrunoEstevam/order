package com.order.brunoestevam.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.service.MessasingConsumerService;
import com.order.brunoestevam.service.OrderProcessingService;

@Service
public class RabbitMQConsumerServiceImpl implements MessasingConsumerService {
	private final OrderProcessingService processingService;

	public RabbitMQConsumerServiceImpl(OrderProcessingService processingService) {
		this.processingService = processingService;
	}

	@RabbitListener(queues = "order.v1.processor")
	public void receiveMessage(OrderMessageRequest request) {
		processingService.process(request);
	}
}
