package com.aditya.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;
    private String orderStatus;

    private String paymentStatus;
    private int orderAmount;
    @Column(length = 1000)
    private String billingAddress;
    private String billingName;
    private String billingPhone;

    private Date orderedDate;
    private Date delhiveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();
}
