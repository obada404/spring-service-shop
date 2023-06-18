package com.mocProject.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private int userId;
    private Date orderDate;
    private Float totalAmount;
    private String status;
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;
    private Date CreatedAt;
    private Date UpdatedAt;

}
