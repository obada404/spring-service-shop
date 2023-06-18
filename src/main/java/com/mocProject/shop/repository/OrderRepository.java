package com.mocProject.shop.repository;

import com.mocProject.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OrderRepository  extends JpaRepository<Order,Integer> {


     Order findByUserId(int userId);
}
