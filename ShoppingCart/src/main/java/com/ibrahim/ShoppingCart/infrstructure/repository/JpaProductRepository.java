package com.ibrahim.ShoppingCart.infrstructure.repository;

import com.ibrahim.ShoppingCart.domain.entity.Product;
import com.ibrahim.ShoppingCart.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaProductRepository implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)",
                Product.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Product> findByCategory(String category) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.category = :category",
                Product.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            entityManager.persist(product);
            return product;
        } else {
            return entityManager.merge(product);
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    @Override
    public List<Product> findByStockQuantityGreaterThan(Integer quantity) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.stockQuantity > :quantity",
                Product.class);
        query.setParameter("quantity", quantity);
        return query.getResultList();
    }
}