package com.order.brunoestevam.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.service.CalculateOrderPrice;
import com.order.brunoestevam.service.OrderProcessor;

@Service
public class CreateOrderServiceImpl implements OrderProcessor {

	private final CalculateOrderPrice calculateOrderPrice;
	
	public CreateOrderServiceImpl(CalculateOrderPrice calculateOrderPrice) {
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
