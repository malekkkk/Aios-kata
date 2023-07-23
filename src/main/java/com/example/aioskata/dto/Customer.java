package com.example.aioskata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Schema(name = "customerId",
            type = "int",
            description = "customer Id")
    private int customerId;

    @NotBlank
    @Schema(name = "name",
            type = "String",
            description = "customer name")
    private String name;

    @NotBlank
    @Schema(name = "address",
            type = "String",
            description = "customer address")
    private String address;

    @NotBlank
    @Schema(name = "postalCode",
            type = "String",
            description = "customer postal code")
    private String postalCode;

    @NotBlank
    @Schema(name = "city",
            type = "String",
            description = "customer city")
    private String city;

    @NotBlank
    @Schema(name = "country",
            type = "String",
            description = "customer country")
    private String country;



}
