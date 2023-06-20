package com.mocProject.shop.controller;

import com.mocProject.shop.DTO.OrderDTO;
import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.service.OrderProductService;
import com.mocProject.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shop/orders")
public class OrderController {

    private final OrderProductService orderProductService;
    private final OrderService orderService;
    private LocalDateTime lastResponseTime;

    @Autowired
    public OrderController( OrderProductService orderProductService
            , OrderService orderService) {
        this.orderProductService = orderProductService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByOrderId(@PathVariable Integer orderId  ,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        List<ProductDTO> allByOrderId = orderProductService.getProductsForOrder(orderId,pageable);
        return ResponseEntity.of(Optional.ofNullable(allByOrderId));

    }

    @PostMapping ("/{userId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable int userId) {

        OrderDTO orderDTO= orderService.createOrder(userId);
        if(orderDTO == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(orderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Page<OrderDTO> orderDTOs = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(orderDTOs);
    }


    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam String status
    ) {

        OrderDTO orderDTO = orderService.updateOrderStatus(orderId, status);
        if (orderDTO != null)
            return ResponseEntity.ok(orderDTO);
        else
            return ResponseEntity.notFound().build();




    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Integer orderId) {

        OrderDTO orderDTO = orderService.cancelOrder(orderId);
        if (orderDTO != null)
            return ResponseEntity.ok(orderDTO);
        else
            return ResponseEntity.notFound().build();


    }
    public ResponseEntity<String> fallbackMethod(Exception ex) {
        return ResponseEntity.ok("Fallback response" +
                "\ninventory service down  " +
                "\nlast time was up is "+lastResponseTime );
    }
}
