package com.order.brunoestevam.service;

import com.order.brunoestevam.dto.OrderMessageRequest;

public interface MessasingConsumerService {

	void receiveMessage(OrderMessageRequest request);

}
