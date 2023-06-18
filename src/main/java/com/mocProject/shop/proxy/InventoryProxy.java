package com.mocProject.shop.proxy;



import com.mocProject.shop.DTO.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory")
public interface InventoryProxy {

    @GetMapping("inventory/decrease-item")
    public String retrieveDecreaseItem();


    @GetMapping("inventory/products/byIds")
    public List<ProductDTO> retrieveProductsByIds(@RequestParam("ids") List<Integer> ids);

    @GetMapping("inventory/products/{productId}")
    public ProductDTO retrieveProductById(@PathVariable("productId") int productId);

    @GetMapping("inventory/products")
    Page<ProductDTO> retrieveAllProducts(@RequestParam("page") int page, @RequestParam("size") int size);
}
