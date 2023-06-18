package com.mocProject.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NonNull
    private int userId;

    private float totalAmount;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartProduct> shoppingCartProducts;

    private Date CreatedAt;
    private Date UpdatedAt;

}
