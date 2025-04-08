package com.example.Demo_E_Commerce.repository;

import com.example.Demo_E_Commerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart , Integer> {
    Cart findByUsers_Id(int userId);
}
