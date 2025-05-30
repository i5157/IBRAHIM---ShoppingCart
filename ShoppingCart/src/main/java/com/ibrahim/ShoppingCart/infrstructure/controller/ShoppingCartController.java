package com.ibrahim.ShoppingCart.infrstructure.controller;

import com.ibrahim.ShoppingCart.domain.entity.ShoppingCart;
import com.ibrahim.ShoppingCart.domain.entity.Product;
import com.ibrahim.ShoppingCart.domain.repository.ShoppingCartRepository;
import com.ibrahim.ShoppingCart.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ShoppingCart createCart() {
        ShoppingCart cart = new ShoppingCart();
        return shoppingCartRepository.save(cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long cartId) {
        Optional<ShoppingCart> cart = shoppingCartRepository.findById(cartId);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ShoppingCart> addProductToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        Optional<ShoppingCart> cartOpt = shoppingCartRepository.findById(cartId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (cartOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (productOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ShoppingCart cart = cartOpt.get();
        Product product = productOpt.get();

        if (product.getStockQuantity() < quantity) {
            return ResponseEntity.badRequest().build();
        }

        cart.addItem(product, quantity);

        product.reduceStock(quantity);
        productRepository.save(product);

        ShoppingCart savedCart = shoppingCartRepository.save(cart);
        return ResponseEntity.ok(savedCart);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<ShoppingCart> removeProductFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {

        Optional<ShoppingCart> cartOpt = shoppingCartRepository.findById(cartId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (cartOpt.isEmpty() || productOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ShoppingCart cart = cartOpt.get();
        Product product = productOpt.get();

        cart.removeItem(product);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        return ResponseEntity.ok(savedCart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ShoppingCart> emptyCart(@PathVariable Long cartId) {
        Optional<ShoppingCart> cartOpt = shoppingCartRepository.findById(cartId);

        if (cartOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ShoppingCart cart = cartOpt.get();
        cart.clearItems();
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        return ResponseEntity.ok(savedCart);
    }

    @PostMapping("/{cartId}/payment")
    public ResponseEntity<String> payForCart(@PathVariable Long cartId) {
        Optional<ShoppingCart> cartOpt = shoppingCartRepository.findById(cartId);

        if (cartOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ShoppingCart cart = cartOpt.get();

        if (cart.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        cart.markAsPaid();
        shoppingCartRepository.save(cart);

        return ResponseEntity.ok("Payment successful. Total paid: $" + cart.getTotal());
    }
}