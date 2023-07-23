package com.example.aioskata.controller;

import com.example.aioskata.api.CustomerApi;
import com.example.aioskata.dto.Customer;
import com.example.aioskata.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;
    @Override
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerService.save(customer);
    }

    @Override
    public Customer updateCustomer(int customerId, Customer customer) {
        return customerService.update(customerId, customer);
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(int customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}
