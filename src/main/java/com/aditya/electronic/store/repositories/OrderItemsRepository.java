package com.aditya.electronic.store.repositories;

import com.aditya.electronic.store.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer> {
}
