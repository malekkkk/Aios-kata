package com.example.aioskata.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PurchaseOrderEntity {

    private int purchaseOrderId;
    private int customerId;
    private LocalDate deliveryDate;
    private int quantity;
    private float price;
    private LocalDate creationDate;

}
