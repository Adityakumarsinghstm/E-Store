package com.aditya.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemsDto {

    private int orderItemId;

    private int quantity;
    private int totalPrice;

    private ProductDto product;

}
