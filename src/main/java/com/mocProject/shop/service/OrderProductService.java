package com.mocProject.shop.service;

import com.mocProject.shop.DTO.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderProductService {


    List<ProductDTO> getProductsForOrder(Integer orderId, Pageable pageable);

}
