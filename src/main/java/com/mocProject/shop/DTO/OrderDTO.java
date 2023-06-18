package com.mocProject.shop.DTO;

import com.mocProject.shop.entity.OrderProduct;
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
public class OrderDTO {

    private Integer id;
    private int userId;
    private Date orderDate;
    private Float totalAmount;
    private String status;

    // try to change name of orderProduct  entity in response
    private List<OrderProduct> OrderProduct;





}
