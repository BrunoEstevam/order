package com.order.brunoestevam.service;

import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.exception.Error;

public interface MessasingProducer {

	void sendOrderUpdate(ProcessOrderResponse response);
	
	void sendOrderError(Error error);

}
