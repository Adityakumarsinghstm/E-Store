package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.dtos.AddItemToCartRequest;
import com.aditya.electronic.store.dtos.CartDto;
import com.aditya.electronic.store.entities.Cart;
import com.aditya.electronic.store.entities.CartItem;
import com.aditya.electronic.store.entities.Product;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.repositories.CartRepository;
import com.aditya.electronic.store.repositories.ProductRepository;
import com.aditya.electronic.store.repositories.UserRepository;
import com.aditya.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with this productId Not found In the database !!"));
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found with this userId !! "));
        Cart cart = null;
        try
        {
            cart = cartRepository.findByUser(user).get();
        }
        catch (NoSuchElementException e)
        {
            cart = new Cart();
            cart.setCreatedAt(new Date());
            cart.setCartId(UUID.randomUUID().toString());
        }

        List<CartItem> items = cart.getItems();
        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> updatedItems = items.stream().map(item->{
            if(item.getProduct().getId().equals(productId)){
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

       if(!updated.get())
       {
           CartItem cartItem = CartItem.builder()
                   .quantity(quantity)
                   .totalPrice(quantity * product.getPrice())
                   .cart(cart)
                   .product(product)
                   .build();

           cart.getItems().add(cartItem);
       }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);


    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
