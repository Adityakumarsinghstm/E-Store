package com.aditya.electronic.store.dtos;

import com.aditya.electronic.store.entities.OrderItems;
import com.aditya.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {

    private String orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate=new Date();
    private Date deliveredDate;
    //private UserDto user;
    private List<OrderItemsDto> orderItems = new ArrayList<>();
}
