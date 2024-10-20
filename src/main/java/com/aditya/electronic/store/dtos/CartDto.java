package com.aditya.electronic.store.dtos;

import com.aditya.electronic.store.entities.CartItem;
import com.aditya.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDto {
    private String cartId;

    private Date createdAt;
    private User user;

    private List<CartItem> items = new ArrayList<>();
}
