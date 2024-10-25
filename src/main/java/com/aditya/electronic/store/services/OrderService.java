package com.aditya.electronic.store.services;

import com.aditya.electronic.store.dtos.CreateOrderRequest;
import com.aditya.electronic.store.dtos.OrderDto;
import com.aditya.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(CreateOrderRequest orderDto);

    void removeOrder(String orderId);

    List<OrderDto> getOrderOfUser(String userId);

    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}
