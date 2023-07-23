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
public class PurchaseOrderToSave {

    @Schema(name = "customerId",
            type = "int",
            description = "customer Id")
    private int customerId;
    @Schema(name = "quantity",
            type = "int",
            description = "quantity")
    private int quantity;
    @Future
    @Schema(name = "deliveryDate",
            type = "LocalDate",
            description = "delivery date")
    private LocalDate deliveryDate;

}
