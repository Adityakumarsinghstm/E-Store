package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.AddItemToCartRequest;
import com.aditya.electronic.store.dtos.ApiResponseMessage;
import com.aditya.electronic.store.dtos.CartDto;
import com.aditya.electronic.store.services.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Tag(name = "Cart Controller",description = "This is cart api for cart operation")
@SecurityRequirement(name = "scheme1")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request)
    {
        CartDto cartDto = cartService.addItemToCart(userId,request);
        return new ResponseEntity(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId)
    {
        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Item removed from Cart Successfully !!")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId)
    {
        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Cart Cleared Successfully !!")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    public ResponseEntity<CartDto> getItemToCart(@PathVariable String userId)
    {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity(cartDto, HttpStatus.OK);
    }

}
