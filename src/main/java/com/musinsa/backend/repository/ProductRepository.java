package com.musinsa.backend.repository;

import com.musinsa.backend.model.Category;
import com.musinsa.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
