package com.ibrahim.ShoppingCart.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ibrahim_alzahrani_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Double taxRate;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private String category;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Product() {
        this.createdDate = LocalDateTime.now();
    }

    public Product(String name, String description, BigDecimal price,
                   Double taxRate, Integer stockQuantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.taxRate = taxRate;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal calculateTaxAmount() {
        return price.multiply(BigDecimal.valueOf(taxRate));
    }

    public BigDecimal getPriceWithTax() {
        return price.add(calculateTaxAmount());
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void reduceStock(int quantity) {
        if (stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock");
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}