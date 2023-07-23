package com.example.aioskata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Schema(name = "purchaseOrderId",
            type = "int",
            description = "purchaseOrderId")
    private int purchaseOrderId;
    @Schema(name = "quantity",
            type = "int",
            description = "quantity")
    private int quantity;
    @Future
    @Schema(name = "deliveryDate",
            type = "LocalDate",
            description = "delivery date")
    private LocalDate deliveryDate;
    @Schema(name = "price",
            type = "float",
            description = "total price of the purchase order")
    private float price;

    @Schema(name = "customer",
            type = "Customer",
            description = "customer")
    private Customer customer;

}
