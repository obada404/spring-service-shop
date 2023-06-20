package com.mocProject.shop.controller;

import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.proxy.InventoryProxy;
import com.mocProject.shop.service.OrderProductService;
import com.mocProject.shop.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/shop/products")
public class ProductController {


    private OrderProductService orderProductService;
    private OrderService orderService;
    private final InventoryProxy inventoryProxy;

    private LocalDateTime lastResponseTime;

    @Autowired
    public ProductController(OrderProductService orderProductService, OrderService orderService, InventoryProxy inventoryProxy) {

        this.orderProductService = orderProductService;
        this.orderService = orderService;
        this.inventoryProxy = inventoryProxy;
    }
    @GetMapping("")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Page<ProductDTO> products = inventoryProxy.retrieveAllProducts(page,size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        ProductDTO product = inventoryProxy.retrieveProductById(id);
        lastResponseTime = LocalDateTime.now();
        return ResponseEntity.of(Optional.ofNullable(product));
    }

    public ResponseEntity<String> fallbackMethod(Exception ex) {
        return ResponseEntity.ok("Fallback response" +
                "\ninventory service down  " +
                "\nlast time was up is "+lastResponseTime );
    }
}
