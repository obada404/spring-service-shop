package com.mocProject.shop.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class OrderProductId implements Serializable {
    private Order order;

    private int productId;

}