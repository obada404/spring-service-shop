package com.mocProject.shop.controller;

import com.mocProject.shop.DTO.OrderDTO;
import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.entity.Order;
import com.mocProject.shop.proxy.InventoryProxy;
import com.mocProject.shop.proxy.WalletProxy;
import com.mocProject.shop.repository.OrderProductRepository;
import com.mocProject.shop.repository.OrderRepository;
import com.mocProject.shop.service.OrderProductService;
import com.mocProject.shop.service.OrderService;
import com.mocProject.shop.service.ShoppingCartProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shop")
public class Controller {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final ShoppingCartProductService shoppingCartProductService;

    private final WalletProxy walletProxy;
    private final InventoryProxy inventoryProxy;
    private OrderProductService orderProductService;
    private OrderService orderService;
    private ModelMapper modelMapper;

    @Autowired
    public Controller(OrderRepository orderRepository, OrderProductRepository orderProductRepository,
                      ShoppingCartProductService shoppingCartProductService, WalletProxy walletProxy,
                      InventoryProxy inventoryProxy, OrderProductService orderProductService, OrderService orderService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.shoppingCartProductService = shoppingCartProductService;
        this.walletProxy = walletProxy;
        this.inventoryProxy = inventoryProxy;
        this.orderProductService = orderProductService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {

      Order order=  orderRepository.findById(id).get();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setOrderProduct(order.getOrderProducts());
        return ResponseEntity.ok( orderDTO);
        // return orderProduct dto insted of orderProduct

    }

    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByOrderId(@PathVariable Integer orderId  ,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<ProductDTO> allByOrderId = orderProductService.getProductsForOrder(orderId,pageable);
        return ResponseEntity.of(Optional.ofNullable(allByOrderId));
    }

    @PostMapping ("/orders/{userId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable int userId) {

        Order order= orderService.createOrder(userId);
        if(order == null)
            return ResponseEntity.notFound().build();

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return ResponseEntity.ok(orderDTO);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        Page<OrderDTO> orderDTOs = orders.map(order -> modelMapper.map(order, OrderDTO.class));

        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductDTO> products = inventoryProxy.retrieveAllProducts(page,size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO product = inventoryProxy.retrieveProductById(id);
        return ResponseEntity.of(Optional.ofNullable(product));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam String status
    ) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus("Cancelled");
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public  String hardcodedResponse(Exception ex){

        return "Fallback-response";
    }

}
