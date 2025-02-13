package com.order.brunoestevam.service;

import com.order.brunoestevam.dto.ProcessOrderResponse;

public interface MessasingProducer {

	void sendOrderUpdate(ProcessOrderResponse response);

}
