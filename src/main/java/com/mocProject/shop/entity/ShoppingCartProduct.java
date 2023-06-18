package com.mocProject.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mocProject.shop.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
@IdClass(ShoppingCartProductId.class)
public class ShoppingCartProduct {



    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    @JsonIgnore
    private ShoppingCart shoppingCart;
    @Id
    private int productId;

    private Integer quantity;

    private Float unitPrice;

    private Float totalPrice;

    private Boolean inStock;


    private Date CreatedAt;
    private Date UpdatedAt;

    public void setProduct(ProductDTO productDTOS,int quantity) {

        this.productId=productDTOS.getId();
        this.quantity=quantity;
        this.unitPrice=productDTOS.getPrice();
        this.totalPrice=productDTOS.getPrice()*quantity;
        this.inStock=productDTOS.isInStock();

    }
}
