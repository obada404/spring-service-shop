package com.mocProject.shop.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartProductId implements Serializable {
    private ShoppingCart shoppingCart;

    private int productId;

}