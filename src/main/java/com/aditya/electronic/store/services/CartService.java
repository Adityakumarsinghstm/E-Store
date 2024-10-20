package com.aditya.electronic.store.services;

import com.aditya.electronic.store.dtos.AddItemToCartRequest;
import com.aditya.electronic.store.dtos.CartDto;

public interface CartService {
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    void removeItemFromCart(String userId, int cartItem);

    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
