package com.mocProject.shop.service;

import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartProductService {


    ShoppingCart addProduct(int product, int userId);

    ShoppingCart deleteProduct(int productId, int userId);

    ShoppingCart incrementQuantity(int productId, int userId);

    ShoppingCart decrementQuantity(int productId, int userId);

    ShoppingCart getShoppingCart(int userId);

    List<ProductDTO> getShoppingCartProducts(int userId);

    ShoppingCart createShoppingCart(int userId);
}
