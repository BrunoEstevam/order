package com.order.brunoestevam.service;

import com.order.brunoestevam.repository.OrderEntity;

public interface OrderProcessor  {
	
    boolean isStatus(String status);

	OrderEntity processor(OrderEntity entity);

}
