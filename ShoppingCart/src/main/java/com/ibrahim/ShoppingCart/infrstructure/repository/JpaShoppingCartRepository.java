package com.ibrahim.ShoppingCart.infrstructure.repository;

import com.ibrahim.ShoppingCart.domain.entity.ShoppingCart;
import com.ibrahim.ShoppingCart.domain.entity.CartStatus;
import com.ibrahim.ShoppingCart.domain.repository.ShoppingCartRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaShoppingCartRepository implements ShoppingCartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        if (shoppingCart.getId() == null) {
            entityManager.persist(shoppingCart);
            return shoppingCart;
        } else {
            return entityManager.merge(shoppingCart);
        }
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {

        TypedQuery<ShoppingCart> query = entityManager.createQuery(
                "SELECT sc FROM ShoppingCart sc " +
                        "LEFT JOIN FETCH sc.items ci " +
                        "LEFT JOIN FETCH ci.product " +
                        "WHERE sc.id = :id",
                ShoppingCart.class);
        query.setParameter("id", id);

        try {
            ShoppingCart cart = query.getSingleResult();
            return Optional.of(cart);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ShoppingCart> findAll() {
        TypedQuery<ShoppingCart> query = entityManager.createQuery(
                "SELECT sc FROM ShoppingCart sc", ShoppingCart.class);
        return query.getResultList();
    }

    @Override
    public List<ShoppingCart> findByStatus(CartStatus status) {
        TypedQuery<ShoppingCart> query = entityManager.createQuery(
                "SELECT sc FROM ShoppingCart sc WHERE sc.status = :status",
                ShoppingCart.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        ShoppingCart cart = entityManager.find(ShoppingCart.class, id);
        if (cart != null) {
            entityManager.remove(cart);
        }
    }
}