package com.order.brunoestevam.mapper;

import com.order.brunoestevam.dto.ProcessOrderItemRequest;
import com.order.brunoestevam.dto.ProcessOrderItemResponse;
import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-13T14:52:01-0300",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderEntity mapToEntity(ProcessOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setIdCustomer( request.getIdCustomer() );
        orderEntity.setStatus( request.getStatus() );
        orderEntity.setItems( processOrderItemRequestListToItemEntityList( request.getItems() ) );

        return orderEntity;
    }

    @Override
    public ProcessOrderRequest mapToRequest(OrderEntity request) {
        if ( request == null ) {
            return null;
        }

        ProcessOrderRequest processOrderRequest = new ProcessOrderRequest();

        processOrderRequest.setIdCustomer( request.getIdCustomer() );
        processOrderRequest.setStatus( request.getStatus() );
        processOrderRequest.setItems( itemEntityListToProcessOrderItemRequestList( request.getItems() ) );

        return processOrderRequest;
    }

    @Override
    public ProcessOrderResponse mapToResponse(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProcessOrderResponse processOrderResponse = new ProcessOrderResponse();

        processOrderResponse.setId( entity.getId() );
        processOrderResponse.setIdCustomer( entity.getIdCustomer() );
        processOrderResponse.setStatus( entity.getStatus() );
        processOrderResponse.setItems( itemEntityListToProcessOrderItemResponseList( entity.getItems() ) );

        return processOrderResponse;
    }

    protected ItemEntity processOrderItemRequestToItemEntity(ProcessOrderItemRequest processOrderItemRequest) {
        if ( processOrderItemRequest == null ) {
            return null;
        }

        ItemEntity itemEntity = new ItemEntity();

        itemEntity.setQuantity( processOrderItemRequest.getQuantity() );
        itemEntity.setDescription( processOrderItemRequest.getDescription() );
        itemEntity.setPrice( processOrderItemRequest.getPrice() );

        return itemEntity;
    }

    protected List<ItemEntity> processOrderItemRequestListToItemEntityList(List<ProcessOrderItemRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemEntity> list1 = new ArrayList<ItemEntity>( list.size() );
        for ( ProcessOrderItemRequest processOrderItemRequest : list ) {
            list1.add( processOrderItemRequestToItemEntity( processOrderItemRequest ) );
        }

        return list1;
    }

    protected ProcessOrderItemRequest itemEntityToProcessOrderItemRequest(ItemEntity itemEntity) {
        if ( itemEntity == null ) {
            return null;
        }

        ProcessOrderItemRequest processOrderItemRequest = new ProcessOrderItemRequest();

        processOrderItemRequest.setQuantity( itemEntity.getQuantity() );
        processOrderItemRequest.setDescription( itemEntity.getDescription() );
        processOrderItemRequest.setPrice( itemEntity.getPrice() );

        return processOrderItemRequest;
    }

    protected List<ProcessOrderItemRequest> itemEntityListToProcessOrderItemRequestList(List<ItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ProcessOrderItemRequest> list1 = new ArrayList<ProcessOrderItemRequest>( list.size() );
        for ( ItemEntity itemEntity : list ) {
            list1.add( itemEntityToProcessOrderItemRequest( itemEntity ) );
        }

        return list1;
    }

    protected ProcessOrderItemResponse itemEntityToProcessOrderItemResponse(ItemEntity itemEntity) {
        if ( itemEntity == null ) {
            return null;
        }

        ProcessOrderItemResponse processOrderItemResponse = new ProcessOrderItemResponse();

        processOrderItemResponse.setId( itemEntity.getId() );
        processOrderItemResponse.setQuantity( itemEntity.getQuantity() );
        processOrderItemResponse.setDescription( itemEntity.getDescription() );
        processOrderItemResponse.setPrice( itemEntity.getPrice() );

        return processOrderItemResponse;
    }

    protected List<ProcessOrderItemResponse> itemEntityListToProcessOrderItemResponseList(List<ItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ProcessOrderItemResponse> list1 = new ArrayList<ProcessOrderItemResponse>( list.size() );
        for ( ItemEntity itemEntity : list ) {
            list1.add( itemEntityToProcessOrderItemResponse( itemEntity ) );
        }

        return list1;
    }
}
