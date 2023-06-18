package com.mocProject.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mocProject.shop.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
@IdClass(OrderProductId.class)
public class OrderProduct {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
    @Id
    private int productId;
    private int quantity;
    private float unitPrice;
    private float totalPrice;
    private Date CreatedAt;
    private Date UpdatedAt;


}



