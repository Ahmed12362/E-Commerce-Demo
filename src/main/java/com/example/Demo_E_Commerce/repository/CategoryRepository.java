package com.example.Demo_E_Commerce.repository;

import com.example.Demo_E_Commerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Integer> {
}
