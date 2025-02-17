package com.order.brunoestevam.service;

import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.dto.OrderResponse;

public interface MessasingProducerService {

	void sendOrderUpdate(OrderResponse response);
	
	void sendOrder(OrderMessageRequest request);

}
