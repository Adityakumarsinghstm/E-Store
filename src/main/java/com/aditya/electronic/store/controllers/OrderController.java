package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.config.AppConstants;
import com.aditya.electronic.store.dtos.ApiResponseMessage;
import com.aditya.electronic.store.dtos.CreateOrderRequest;
import com.aditya.electronic.store.dtos.OrderDto;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('"+ AppConstants.ROLE_NORMAL +"','"+AppConstants.ROLE_ADMIN+"')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request)
    {
        OrderDto orderDto = orderService.createOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId)
    {
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Order removed Successfully !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("users/{userId}")
    @PreAuthorize("hasAnyRole('"+AppConstants.ROLE_NORMAL+"','"+AppConstants.ROLE_ADMIN+"')")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId)
    {
        List<OrderDto> ordersOfUser = orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
    public ResponseEntity<PageableResponse<OrderDto>> getOders(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir

    )
    {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
