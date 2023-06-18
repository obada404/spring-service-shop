package com.mocProject.shop.service;

import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.entity.OrderProduct;
import com.mocProject.shop.proxy.InventoryProxy;
import com.mocProject.shop.repository.OrderProductRepository;
import com.mocProject.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;


@Component
public class OrderProductServiceImp implements OrderProductService{

    private final
    OrderProductRepository orderProductRepository;

    ShoppingCartProductService shoppingCartProductService;
    private final
    InventoryProxy inventoryProxy;
    private OrderRepository orderRepository;

    @Autowired
    public OrderProductServiceImp(OrderProductRepository orderProductRepository, InventoryProxy inventoryProxy, OrderRepository orderRepository
    , ShoppingCartProductService shoppingCartProductService) {
        this.orderProductRepository = orderProductRepository;
        this.inventoryProxy = inventoryProxy;
        this.orderRepository = orderRepository;
        this.shoppingCartProductService=shoppingCartProductService;
    }

    @Override
    public List<ProductDTO> getProductsForOrder(Integer orderId, Pageable pageable) {

        List<OrderProduct> allByOrderId = orderProductRepository.findAllByOrderId(orderId,pageable);
        List<Integer> idList = allByOrderId.stream()
                .map(OrderProduct::getProductId)
                .collect(Collectors.toList());

        return inventoryProxy.retrieveProductsByIds(idList);
    }


}
