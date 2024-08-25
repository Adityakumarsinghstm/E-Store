package com.aditya.electronic.store.repositories;

import com.aditya.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
