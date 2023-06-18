package com.mocProject.shop.repository;

import com.mocProject.shop.entity.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {
    List<OrderProduct> findAllByOrderId(int orderId, Pageable pageable);

}
