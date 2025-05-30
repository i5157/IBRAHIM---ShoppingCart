package com.ibrahim.ShoppingCart.domain.repository;

import com.ibrahim.ShoppingCart.domain.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(String category);
    Product save(Product product);
    void deleteById(Long id);
    List<Product> findByStockQuantityGreaterThan(Integer quantity);
}