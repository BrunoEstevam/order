package com.order.brunoestevam.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.service.CalculateOrderPriceService;

@Service
public class CalculateOrderPriceServiceImpl implements CalculateOrderPriceService {

	@Override
	public BigDecimal calculate(List<ItemEntity> items) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		for (ItemEntity item : items) {
		    BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
		    int quantity = item.getQuantity() != null ? item.getQuantity() : 0;
		    
		    BigDecimal priceForQuantity = price.multiply(new BigDecimal(quantity));
		    totalPrice = totalPrice.add(priceForQuantity);
		}
		
		return totalPrice;
	}

}
