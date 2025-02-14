package com.order.brunoestevam.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.service.CalculateOrderPriceService;
import com.order.brunoestevam.service.OrderProcessorService;

@Service
public class CreateOrderServiceImpl implements OrderProcessorService {

	private final CalculateOrderPriceService calculateOrderPrice;
	
	public CreateOrderServiceImpl(CalculateOrderPriceService calculateOrderPrice) {
		this.calculateOrderPrice = calculateOrderPrice;
	}

	@Override
	public OrderEntity processor(OrderEntity order) {
		BigDecimal totalPrice = calculateOrderPrice.calculate(order.getItems());

		order.setTotalPrice(totalPrice);
		order.setStatus(OrderStatusEnum.CREATED.getCode());
		
		return order;
	}

	@Override
	public boolean isStatus(String status) {
		return StringUtils.hasLength(status) && status.equals(OrderStatusEnum.CREATED.getCode());
	}

}
