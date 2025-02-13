package com.order.brunoestevam.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.service.CalculateOrderPrice;

@Service
public class CalculateOrderPriceImpl implements CalculateOrderPrice {

	@Override
	public BigDecimal calculate(List<ItemEntity> items) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		for (ItemEntity item : items) {
			BigDecimal priceForQuantity = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
			totalPrice = totalPrice.add(priceForQuantity);
		}
		
		return totalPrice;
	}

}
