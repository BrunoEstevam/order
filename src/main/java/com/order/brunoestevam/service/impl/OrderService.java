package com.order.brunoestevam.service.impl;

import org.springframework.stereotype.Service;

import com.order.brunoestevam.config.OrderProcessorFactory;
import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.MessasingProducerService;

@Service
public class OrderService {

	private final OrderRepository repository;

	private final IdempotenteService idempotenteService;

	private final MessasingProducerService messasingProducerService;

	public OrderService(OrderRepository repository, IdempotenteService idempotenteService,
			OrderProcessorFactory factory, MessasingProducerService messasingProducerService) {
		this.repository = repository;
		this.idempotenteService = idempotenteService;
		this.messasingProducerService = messasingProducerService;
	}

	public OrderResponse save(OrderEntity order, String idempotenteKey) {
		idempotenteService.validateAndPut(idempotenteKey);

		order.setStatus(OrderStatusEnum.PENDING.getCode());
		order.setIdempotentKey(idempotenteKey);
		associateItemsToOrder(order);
		
		repository.save(order);
		messasingProducerService.sendOrder(new OrderMessageRequest(order.getId(), order.getStatus()));

		return OrderMapper.INSTANCE.mapToResponse(order);
	}

	private void associateItemsToOrder(OrderEntity order) {
		for (ItemEntity item : order.getItems()) {
			item.setOrder(order);
		}
	}

	public OrderEntity findById(Long id) throws InterruptedException {
		return repository.findById(id).orElseThrow();
	}
}
