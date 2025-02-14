package com.order.brunoestevam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.order.brunoestevam.config.OrderProcessorFactory;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.IdempotenteService;
import com.order.brunoestevam.service.MessasingProducerService;
import com.order.brunoestevam.service.OrderProcessorService;

@Service
public class OrderProcessingService {

	private final OrderRepository repository;

	private final IdempotenteService idempotenteService;

	private final OrderProcessorFactory factory;

	private final MessasingProducerService messasingProducerService;

	public OrderProcessingService(OrderRepository repository, IdempotenteService idempotenteService,
			OrderProcessorFactory factory, MessasingProducerService messasingProducerService) {
		this.repository = repository;
		this.idempotenteService = idempotenteService;
		this.factory = factory;
		this.messasingProducerService = messasingProducerService;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
	public ProcessOrderResponse process(OrderEntity order, String idempotenteKey) {
		idempotenteService.validate(idempotenteKey);

		order = processWithFactory(order);

		order.setIdempotentKey(idempotenteKey);
		associateItemsToOrder(order);

		repository.save(order);

		ProcessOrderResponse response = OrderMapper.INSTANCE.mapToResponse(order);

		messasingProducerService.sendOrderUpdate(response);
		idempotenteService.put(idempotenteKey);

		return response;
	}

	private OrderEntity processWithFactory(OrderEntity order) {
		OrderProcessorService orderProcessor = factory.getProcessor(order.getStatus());
		return orderProcessor.processor(order);
	}

	private void associateItemsToOrder(OrderEntity order) {
		for (ItemEntity item : order.getItems()) {
			item.setOrder(order);
		}
	}
}
