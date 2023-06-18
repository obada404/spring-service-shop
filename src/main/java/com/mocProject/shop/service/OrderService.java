package com.mocProject.shop.service;

import com.mocProject.shop.entity.Order;

public  interface OrderService {

    public abstract Order createOrder(int userId);
}
