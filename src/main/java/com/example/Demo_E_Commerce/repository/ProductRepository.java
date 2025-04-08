package com.example.Demo_E_Commerce.repository;

import com.example.Demo_E_Commerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Integer> {

}
