package com.example.aioskata.api;

import com.example.aioskata.dto.PurchaseOrder;
import com.example.aioskata.dto.PurchaseOrderToSave;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Ref purchase order",
        description = "Ref purchase order"
)
@RequestMapping("/api/purchase-orders")
public interface PurchaseOrderApi {
    @Operation(description = "Get all purchase orders maded by the given customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of banana purchase order ", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = PurchaseOrder.class)))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404",
                    description = "The resource is not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Technical error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) })
    })
    @GetMapping()
    List<PurchaseOrder> getCustomerPurchaseOrders(@RequestParam("customerId") @Valid @NotNull Integer customerId);

    @Operation(description = "Save purchase order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Saved purchase order", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = PurchaseOrder.class)))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404",
                    description = "The resource is not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Technical error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) })
    })
    @PostMapping()
    PurchaseOrder savePurchaseOrder(@RequestBody @Valid @NotNull PurchaseOrderToSave bananaPurchaseOrder);

    @Operation(description = "Update purchase order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated purchase order", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = PurchaseOrder.class)))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404",
                    description = "The resource is not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Technical error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) })
    })
    @PutMapping("/{purchaseOrderId}")
    PurchaseOrder updatePurchaseOrder(@PathVariable("purchaseOrderId") int purchaseOrderId,
                                      @RequestBody @Valid @NotNull PurchaseOrderToSave purchaseOrder);

    @Operation(description = "delete purchase order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Purchase order deleted"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404",
                    description = "The resource is not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Technical error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) })
    })
    @DeleteMapping("/{purchaseOrderId}")
    ResponseEntity<Void> deletePurchaseOrder(@PathVariable("purchaseOrderId") int purchaseOrderId);


}
