package com.mocProject.shop.repository;

import com.mocProject.shop.entity.Order;
import com.mocProject.shop.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {


    ShoppingCart findByUserId(int userId);
}
