package com.mocProject.shop.DTO;


import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDTO {

    private int id;
    private float amount;
    private Date transactionDate;
}
