package com.aditya.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private String cartId;
    private String userId;

    private String orderStatus = "PENDING";

    private String paymentStatus = "NOTPAID";
    private String billingAddress;
    private String billingName;
    private String billingPhone;


}
