package com.aditya.electronic.store.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItemDto {
    private int cartItemId;
    private ProductDto product;

    private int quantity;
    private int totalPrice;
}
