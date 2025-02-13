package com.order.brunoestevam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.repository.OrderEntity;

@Mapper
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

	OrderEntity mapToEntity(ProcessOrderRequest request);

	ProcessOrderRequest mapToRequest(OrderEntity request);
	
	ProcessOrderResponse mapToResponse(OrderEntity entity);
}
