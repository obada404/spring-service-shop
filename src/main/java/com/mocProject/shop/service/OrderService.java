package com.mocProject.shop.service;

import com.mocProject.shop.DTO.OrderDTO;
import org.springframework.data.domain.Page;

public  interface OrderService {

    public abstract OrderDTO createOrder(int userId);

    OrderDTO getOrderById(Integer id);

    void deleteOrder(Integer id);

    Page<OrderDTO> getAllOrders(int page, int size);

    OrderDTO updateOrderStatus(Integer orderId, String status);

    OrderDTO cancelOrder(Integer orderId);
}
