package com.aditya.electronic.store.repositories;

import com.aditya.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keyword);
}
