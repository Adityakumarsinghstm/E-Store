package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.dtos.CreateOrderRequest;
import com.aditya.electronic.store.dtos.OrderDto;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.entities.*;
import com.aditya.electronic.store.exceptions.BadApiRequestException;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.repositories.CartRepository;
import com.aditya.electronic.store.repositories.OrderRepository;
import com.aditya.electronic.store.repositories.UserRepository;
import com.aditya.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found With this Id !!"));

        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found with this Id !!"));

        List<CartItem> cartItems = cart.getItems();
        if(cartItems.size()<=0)
        {
            throw new BadApiRequestException("Invalid number of items in cart !!");
        }

        Order order = Order.builder()
                .billingAddress(orderDto.getBillingAddress())
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .orderedDate(new Date())
                .delhiveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount = new AtomicReference<>();
        List<OrderItems> orderItems = cartItems.stream().map(cartItem -> {

            OrderItems orderItem = OrderItems.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order  savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeDto(String orderId) {

    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        return List.of();
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
