package com.ibrahim.ShoppingCart.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ibrahim_alzahrani_shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CartStatus status;

    public ShoppingCart() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.status = CartStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public void addItem(Product product, Integer quantity) {
        // Check if product already exists in cart
        CartItem existingItem = findItemByProduct(product);

        if (existingItem != null) {
            existingItem.increaseQuantity(quantity);
        } else {
            CartItem newItem = new CartItem(product, quantity, this);
            items.add(newItem);
        }

        this.updatedDate = LocalDateTime.now();
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
        this.updatedDate = LocalDateTime.now();
    }

    public void clearItems() {
        items.clear();
        this.updatedDate = LocalDateTime.now();
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalTax() {
        return items.stream()
                .map(CartItem::getTaxAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getTotalTax());
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void markAsPaid() {
        this.status = CartStatus.PAID;
        this.updatedDate = LocalDateTime.now();
    }

    private CartItem findItemByProduct(Product product) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
    }
}