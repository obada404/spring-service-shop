package com.mocProject.shop.service;

import com.mocProject.shop.DTO.TransactionDTO;
import com.mocProject.shop.entity.Order;
import com.mocProject.shop.entity.OrderProduct;
import com.mocProject.shop.entity.ShoppingCart;
import com.mocProject.shop.entity.ShoppingCartProduct;
import com.mocProject.shop.proxy.InventoryProxy;
import com.mocProject.shop.proxy.WalletProxy;
import com.mocProject.shop.repository.OrderProductRepository;
import com.mocProject.shop.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderServiceImp implements OrderService {

    private final
    OrderProductRepository orderProductRepository;

    private ModelMapper modelMapper;

    ShoppingCartProductService shoppingCartProductService;
    private final
    InventoryProxy inventoryProxy;
    private final
    WalletProxy walletProxy;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImp(OrderProductRepository orderProductRepository, InventoryProxy inventoryProxy, OrderRepository orderRepository
            , ShoppingCartProductService shoppingCartProductService, WalletProxy walletProxy
    ,ModelMapper modelMapper) {
        this.orderProductRepository = orderProductRepository;
        this.inventoryProxy = inventoryProxy;
        this.orderRepository = orderRepository;
        this.shoppingCartProductService=shoppingCartProductService;
        this.walletProxy = walletProxy;
        this.modelMapper= modelMapper;
    }
    @Override
    public Order createOrder(int userId) {

        ShoppingCart shoppingCart = shoppingCartProductService.getShoppingCart(userId);
        float totalAmount =0;

        Order newOrder= orderRepository.save(new Order());

        newOrder.setUserId(userId);
        newOrder.setOrderDate(getNowDate());

        totalAmount = mapShoppingCartToOrder(shoppingCart, totalAmount, newOrder);

        addTransaction(userId, -totalAmount);

        orderRepository.save(newOrder);

        return newOrder;

    }

    private float mapShoppingCartToOrder(ShoppingCart shoppingCart, float totalAmount, Order newOrder) {
        List<OrderProduct> orderProducts= new ArrayList<>();

        for (ShoppingCartProduct shoppingCartProduct: shoppingCart.getShoppingCartProducts() ) {

            OrderProduct  orderProduct = new OrderProduct();
            orderProduct=  modelMapper.map(shoppingCartProduct,OrderProduct.class);
            orderProduct.setOrder(newOrder);
            orderProductRepository.save(orderProduct);

            orderProducts.add(orderProduct);

            totalAmount += shoppingCartProduct.getTotalPrice();
        }

        newOrder.setOrderProducts(orderProducts);
        newOrder.setTotalAmount(totalAmount);
        return totalAmount;
    }

    private void addTransaction(int userId, float totalAmount) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(totalAmount);
        transactionDTO.setTransactionDate(getNowDate());
        walletProxy.addTransaction(userId,transactionDTO);
    }

    private static Date getNowDate() {
        Date date = new Date();

        LocalTime localTime = LocalTime.now();

        date.setHours(localTime.getHour());
        date.setMinutes(localTime.getMinute());
        date.setSeconds(0);
        return date;
    }
}
