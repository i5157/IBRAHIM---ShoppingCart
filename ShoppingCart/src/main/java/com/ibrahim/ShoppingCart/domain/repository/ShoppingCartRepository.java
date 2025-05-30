package com.ibrahim.ShoppingCart.domain.repository;

import com.ibrahim.ShoppingCart.domain.entity.ShoppingCart;
import com.ibrahim.ShoppingCart.domain.entity.CartStatus;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {
    ShoppingCart save(ShoppingCart shoppingCart);
    Optional<ShoppingCart> findById(Long id);
    List<ShoppingCart> findAll();
    List<ShoppingCart> findByStatus(CartStatus status);
    void deleteById(Long id);
}