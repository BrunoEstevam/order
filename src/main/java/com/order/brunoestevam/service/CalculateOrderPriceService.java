package com.order.brunoestevam.service;

import java.math.BigDecimal;
import java.util.List;

import com.order.brunoestevam.repository.ItemEntity;

public interface CalculateOrderPriceService {
	
	BigDecimal calculate(List<ItemEntity> items);

}
