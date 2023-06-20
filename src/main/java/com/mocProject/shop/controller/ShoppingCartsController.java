package com.mocProject.shop.controller;

import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.entity.ShoppingCart;
import com.mocProject.shop.service.ShoppingCartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/shopping-carts")
public class ShoppingCartsController {



    private final ShoppingCartProductService shoppingCartProductService;


    @Autowired
    public ShoppingCartsController(ShoppingCartProductService shoppingCartProductService ) {
        this.shoppingCartProductService = shoppingCartProductService;
    }
    @GetMapping("/{userId}/products")
    public List<ProductDTO> getShoppingCartProducts(@PathVariable int userId   ) {

        return shoppingCartProductService.getShoppingCartProducts(userId);
    }
    @GetMapping("/{userId}")
    public ShoppingCart getShoppingCart(@PathVariable int userId ) {

        return shoppingCartProductService.getShoppingCart(userId);
    }
    @PostMapping("/{userId}/products/{productId}")
    public ShoppingCart addToShoppingCart(@PathVariable int userId, @PathVariable int productId) {

        return shoppingCartProductService.addProduct(productId,userId);
    }
    @PutMapping("/{userId}/products/{productId}/increment")
    public ShoppingCart incrementQuantityOfProductInShoppingCart(@PathVariable int userId, @PathVariable int productId ) {

        return shoppingCartProductService.incrementQuantity(productId,userId);
    }
    @PutMapping("/{userId}")
    public ShoppingCart createShoppingCart(@PathVariable int userId) {

        return shoppingCartProductService.createShoppingCart(userId);
    }
    @PutMapping("/{userId}/products/{productId}/decrement")
    public ShoppingCart decrementQuantityOfProductInShoppingCart(@PathVariable int userId, @PathVariable int productId ) {

        return shoppingCartProductService.decrementQuantity(productId,userId);
    }
    @DeleteMapping ("/{userId}/products/{productId}")
    public ShoppingCart deleteProductFromShoppingCart(@PathVariable int userId, @PathVariable int productId) {

        return shoppingCartProductService.deleteProduct(productId,userId);
    }



    public  String hardcodedResponse(Exception ex){

        return "Fallback-response";
    }

}
