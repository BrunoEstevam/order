package com.order.brunoestevam.service;

import java.util.Map;

import org.springframework.messaging.handler.annotation.Headers;

import com.order.brunoestevam.dto.ProcessOrderRequest;

public interface MessasingConsumer {

	void receiveMessage(ProcessOrderRequest request, @Headers Map<String, Object> headers);

}
