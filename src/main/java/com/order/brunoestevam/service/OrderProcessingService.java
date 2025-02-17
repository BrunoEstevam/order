package com.order.brunoestevam.service;

import com.order.brunoestevam.dto.OrderMessageRequest;

public interface OrderProcessingService {

	void process(OrderMessageRequest request);
}
