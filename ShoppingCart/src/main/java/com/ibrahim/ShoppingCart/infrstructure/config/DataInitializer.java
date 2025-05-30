package com.ibrahim.ShoppingCart.infrstructure.config;

import com.ibrahim.ShoppingCart.domain.entity.Product;
import com.ibrahim.ShoppingCart.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (productRepository.findAll().isEmpty()) {
            initializeProducts();
        }
    }

    private void initializeProducts() {

        productRepository.save(new Product("Apple", "Red apple",
                new BigDecimal("2.50"), 0.08, 100, "fruits"));

        productRepository.save(new Product("Bread", "Whole wheat bread",
                new BigDecimal("3.00"), 0.08, 50, "bakes"));

        productRepository.save(new Product("Milk", "Full fat milk 1L",
                new BigDecimal("4.50"), 0.08, 30, "dairy"));

        productRepository.save(new Product("Orange Juice", "100% pure orange juice",
                new BigDecimal("5.99"), 0.08, 25, "Drinks"));

        productRepository.save(new Product("T-Shirt", "Oversize t-shirt",
                new BigDecimal("19.99"), 0.15, 20, "clothes"));

        productRepository.save(new Product("Jeans", "Denim jeans",
                new BigDecimal("49.99"), 0.15, 15, "clothes"));

        productRepository.save(new Product("Sneakers", "Air force 1",
                new BigDecimal("89.99"), 0.15, 12, "clothes"));

        productRepository.save(new Product("Dress", "Woman dress",
                new BigDecimal("39.99"), 0.15, 8, "clothes"));

        productRepository.save(new Product("Headphones", "Wireless bluetooth headphones",
                new BigDecimal("79.99"), 0.20, 10, "phone accessories"));

        productRepository.save(new Product("Phone Case", "Protective phone case",
                new BigDecimal("15.99"), 0.20, 25, "phone accessories"));

        productRepository.save(new Product("Quran", "The holy Quran",
                new BigDecimal("45.00"), 0.08, 5, "books"));

        productRepository.save(new Product("Quran in turkish", "The holy Quran translated to turkish",
                new BigDecimal("50.00"), 0.08, 7, "books"));

        System.out.println("Sample products initialized!");
    }
}