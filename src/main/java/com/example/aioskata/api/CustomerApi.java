package com.example.aioskata.api;

import com.example.aioskata.dto.Customer;
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
        name = "Ref Customer",
        description = "Ref Customer"
)
@RequestMapping("/api/customers")
public interface CustomerApi {

    @Operation(description = "get all customerTable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of customerTable", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
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
    List<Customer> getCustomers();

    @Operation(description = "save customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "saved customer", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Customer.class))),
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
    Customer saveCustomer(@RequestBody @Valid @NotNull Customer customer);

    @Operation(description = "update customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "updated customer", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Customer.class))),
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
    @PutMapping("/{customerId}")
    Customer updateCustomer(@PathVariable("customerId") int customerId,
                            @RequestBody @Valid @NotNull Customer customer);


    @Operation(description = "delete customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "customer deleted"),
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
    @DeleteMapping("/{customerId}")
    ResponseEntity<Void> deleteCustomer(int customerId);
}
