package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.dtos.CreateOrderRequest;
import com.aditya.electronic.store.dtos.OrderDto;
import com.aditya.electronic.store.dtos.OrderItemsDto;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.entities.*;
import com.aditya.electronic.store.exceptions.BadApiRequestException;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.helper.Helper;
import com.aditya.electronic.store.repositories.CartRepository;
import com.aditya.electronic.store.repositories.OrderRepository;
import com.aditya.electronic.store.repositories.UserRepository;
import com.aditya.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp  implements OrderService  {
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
        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not found on server !!"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0) {
            throw new BadApiRequestException("Invalid number of items in cart !!");

        }

        //other checks

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

//        orderItems,amount

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItems> orderItems = cartItems.stream().map(cartItem -> {
//            CartItem->OrderItem
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

        //
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found with this Id !!"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with this Id !!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort  sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }
}
