package com.order.brunoestevam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.order.brunoestevam.config.OrderProcessorFactory;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.OrderProcessor;

@Service
public class OrderService {

	private final OrderRepository repository;

	private final IdempotenteServiceImpl idempotenteService;

	private final OrderProcessorFactory factory;

	private final RabbitMQProducer rabbitMQService;

	public OrderService(OrderRepository repository, IdempotenteServiceImpl idempotenteService,
			OrderProcessorFactory factory, RabbitMQProducer rabbitMQService) {
		this.repository = repository;
		this.idempotenteService = idempotenteService;
		this.factory = factory;
		this.rabbitMQService = rabbitMQService;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public OrderEntity process(OrderEntity order, String idempotenteKey) {
		idempotenteService.validate(idempotenteKey);

		OrderProcessor orderProcessor = factory.getProcessor(order.getStatus());
		order = orderProcessor.processor(order);

		repository.save(order);

		rabbitMQService.sendOrderUpdate(order);
		
		idempotenteService.put(idempotenteKey);

		return order;
	}

}
