package com.order.brunoestevam.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.brunoestevam.config.OrderProcessorFactory;
import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.MessasingProducerService;
import com.order.brunoestevam.service.OrderProcessingService;
import com.order.brunoestevam.service.OrderProcessorService;


@Service
public class OrderProcessingServiceImpl implements OrderProcessingService {
	
	private final OrderProcessorFactory factory;
	
	private final OrderRepository orderRepository;
	
	private final MessasingProducerService messasingProducerService;
	
	public OrderProcessingServiceImpl(OrderProcessorFactory factory, OrderRepository orderRepository,
			MessasingProducerService messasingProducerService) {
		this.factory = factory;
		this.orderRepository = orderRepository;
		this.messasingProducerService = messasingProducerService;
	}

	@Override
	@Transactional
	public void process(OrderMessageRequest request) {
		OrderEntity order = orderRepository.findById(request.getId()).orElseThrow();
		
		try {
			getProcessorForOrderStatus(order);
			messasingProducerService.sendOrderUpdate(OrderMapper.INSTANCE.mapToResponse(order));
			
		} catch (Exception e) {
			order.setStatus(OrderStatusEnum.ERROR.getCode());
		}
		
		orderRepository.save(order);
	}
	
	private OrderEntity getProcessorForOrderStatus(OrderEntity order) {
		OrderProcessorService orderProcessor = factory.getProcessor(order.getStatus());
		return orderProcessor.processor(order);
	}

}
