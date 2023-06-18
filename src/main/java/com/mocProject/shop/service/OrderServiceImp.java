package com.mocProject.shop.service;

import com.mocProject.shop.DTO.OrderDTO;
import com.mocProject.shop.DTO.TransactionDTO;
import com.mocProject.shop.entity.Order;
import com.mocProject.shop.entity.OrderProduct;
import com.mocProject.shop.entity.ShoppingCart;
import com.mocProject.shop.entity.ShoppingCartProduct;
import com.mocProject.shop.exeptionHandling.entityNotFoundException;
import com.mocProject.shop.proxy.InventoryProxy;
import com.mocProject.shop.proxy.WalletProxy;
import com.mocProject.shop.repository.OrderProductRepository;
import com.mocProject.shop.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public OrderDTO createOrder(int userId) {

        ShoppingCart shoppingCart = shoppingCartProductService.getShoppingCart(userId);
        float totalAmount =0;

        Order newOrder= orderRepository.save(new Order());

        newOrder.setUserId(userId);
        newOrder.setOrderDate(getNowDate());

        totalAmount = mapShoppingCartToOrder(shoppingCart, totalAmount, newOrder);

        addTransaction(userId, -totalAmount);

        orderRepository.save(newOrder);
        OrderDTO orderDTO = modelMapper.map(newOrder, OrderDTO.class);

        return orderDTO;

    }

    @Override
    public OrderDTO getOrderById(Integer id) {
        Order order=  orderRepository.findById(id).get();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setOrderProduct(order.getOrderProducts());
        return orderDTO;
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<OrderDTO> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        Page<OrderDTO> orderDTOs = orders.map(order -> modelMapper.map(order, OrderDTO.class));

        return orderDTOs;
    }

    @Override
    public OrderDTO updateOrderStatus(Integer orderId, String status) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            return orderDTO;
        }
        else
            throw new entityNotFoundException("there is no order with this  id "+ orderId);
       }

    @Override
    public OrderDTO cancelOrder(Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus("Cancelled");
            orderRepository.save(order);
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

            return orderDTO;
        } else {
           throw  new entityNotFoundException(" there is no order with this Id "+ orderId);
        }
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
