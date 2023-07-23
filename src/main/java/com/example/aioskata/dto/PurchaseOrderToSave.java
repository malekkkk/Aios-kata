package com.example.aioskata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Schema(name = "customerId",
            type = "int",
            description = "customer Id")
    private Integer customerId;
    @NotNull
    @Schema(name = "quantity",
            type = "int",
            description = "quantity")
    private Integer quantity;
    @NotNull
    @Future
    @Schema(name = "deliveryDate",
            type = "LocalDate",
            description = "delivery date")
    private LocalDate deliveryDate;

}
