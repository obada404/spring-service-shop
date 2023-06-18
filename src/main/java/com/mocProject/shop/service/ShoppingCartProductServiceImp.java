package com.mocProject.shop.service;

import com.mocProject.shop.DTO.ProductDTO;
import com.mocProject.shop.entity.ShoppingCart;
import com.mocProject.shop.entity.ShoppingCartProduct;
import com.mocProject.shop.proxy.InventoryProxy;
import com.mocProject.shop.repository.ShoppingCartProductRepository;
import com.mocProject.shop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingCartProductServiceImp implements ShoppingCartProductService{



    private ShoppingCartProductRepository shoppingCartProductRepository;

    private InventoryProxy inventoryProxy;
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartProductServiceImp(ShoppingCartProductRepository shoppingCartProductRepository, InventoryProxy inventoryProxy, ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartProductRepository = shoppingCartProductRepository;
        this.inventoryProxy = inventoryProxy;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public ShoppingCart addProduct(int productId, int userId) {

       ProductDTO productDTOS = inventoryProxy.retrieveProductById(productId);
       ShoppingCart shoppingCartByUserId = shoppingCartRepository.findByUserId(userId);




        if(productDTOS != null && shoppingCartByUserId != null){

            ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
            shoppingCartProduct.setShoppingCart(shoppingCartByUserId);
            shoppingCartProduct.setProduct(productDTOS,1);
            // use mapper insted of set product

            shoppingCartByUserId.getShoppingCartProducts().add(shoppingCartProduct);
            shoppingCartByUserId.setTotalAmount(shoppingCartByUserId.getTotalAmount()+shoppingCartProduct.getTotalPrice());

            shoppingCartProductRepository.save(shoppingCartProduct);

        }

        shoppingCartRepository.save(shoppingCartByUserId);

        return shoppingCartByUserId;


    }


    @Override
    public ShoppingCart deleteProduct(int productId, int userId) {

        ProductDTO productDTOS = inventoryProxy.retrieveProductById(productId);
        ShoppingCart shoppingCartByUserId = shoppingCartRepository.findByUserId(userId);


       ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository.findByProductId(productId);


        if(productDTOS != null ){
            shoppingCartProductRepository.delete(shoppingCartProduct);
        }


        return shoppingCartByUserId;

    }

    @Override
    public ShoppingCart incrementQuantity(int productId, int userId) {

        ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository.findByProductId(productId);
        ShoppingCart shoppingCartByUserId = shoppingCartRepository.findByUserId(userId);

        shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity()+1);
        shoppingCartProductRepository.save(shoppingCartProduct);

        return shoppingCartByUserId;
    }

    @Override
    public ShoppingCart decrementQuantity(int productId, int userId) {

        ShoppingCartProduct shoppingCartProduct = shoppingCartProductRepository.findByProductId(productId);
        ShoppingCart shoppingCartByUserId = shoppingCartRepository.findByUserId(userId);

        shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity()-1);
        shoppingCartProductRepository.save(shoppingCartProduct);

        return shoppingCartByUserId;
    }

    @Override
    public ShoppingCart getShoppingCart(int userId) {

        ShoppingCart shoppingCartByUserId = shoppingCartRepository.findByUserId(userId);
        return shoppingCartByUserId;

    }

    @Override
    public List<ProductDTO> getShoppingCartProducts(int userId) {
        ShoppingCart shoppingCartByUserId = shoppingCartRepository.findByUserId(userId);
        return getProductDTOS(shoppingCartByUserId);
    }

    private List<ProductDTO> getProductDTOS(ShoppingCart shoppingCartByUserId) {
        List<ShoppingCartProduct> allByShoppingCarts = shoppingCartProductRepository.findAllByShoppingCartId(shoppingCartByUserId.getId());
        List<Integer> idList = allByShoppingCarts.stream()
                .map(ShoppingCartProduct::getProductId)
                .collect(Collectors.toList());

        return inventoryProxy.retrieveProductsByIds(idList);
    }
}
