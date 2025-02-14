package com.order.brunoestevam.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.exception.Error;
import com.order.brunoestevam.exception.InvalidDataException;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.service.MessasingConsumerService;
import com.order.brunoestevam.service.MessasingProducerService;

@Service
public class RabbitMQConsumerServiceImpl implements MessasingConsumerService {
	private final OrderProcessingService orderService;

	private final MessasingProducerService producer;

	public RabbitMQConsumerServiceImpl(OrderProcessingService orderService, MessasingProducerService producer) {
		this.orderService = orderService;
		this.producer = producer;
	}

	@RabbitListener(queues = "order.v1.processor")
	public void receiveMessage(ProcessOrderRequest request, @Headers Map<String, Object> headers) {
		try {
			if (!Optional.ofNullable(headers.get("Idempotency-Key")).isPresent()) {
				throw new InvalidDataException("Idempotency-Key is madatory");
			}
			
			orderService.process(OrderMapper.INSTANCE.mapToEntity(request), headers.get("Idempotency-Key").toString());

		} catch (Exception e) {
			String errorType = e.getClass().getSimpleName();

			Error error = new Error();
			error.setDate(new Date());
			error.setMessages(e.getMessage());
			error.setExceptionClass(errorType);

			producer.sendOrderError(error);
		}
	}
}
