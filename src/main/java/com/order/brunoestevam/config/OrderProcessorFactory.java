package com.order.brunoestevam.config;

import java.util.List;

import org.springframework.stereotype.Component;

import com.order.brunoestevam.exception.InvalidDataException;
import com.order.brunoestevam.service.OrderProcessorService;

@Component
public class OrderProcessorFactory {
	
    private final List<OrderProcessorService> processors;

    public OrderProcessorFactory(List<OrderProcessorService> processors) {
		this.processors = processors;
	}

	public OrderProcessorService getProcessor(String status) {
       return processors.stream()
                .filter(processor -> processor.isStatus(status)) 
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("No processor available for status: " + status));
    }
}
