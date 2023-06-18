package com.mocProject.shop.repository;

import com.mocProject.shop.entity.ShoppingCart;
import com.mocProject.shop.entity.ShoppingCartProduct;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(exported = false)
@Repository
public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct,Integer> {
    List<ShoppingCartProduct> findAllByShoppingCartId( int userId);

    ShoppingCartProduct findByProductId(int productId);

}
