package com.example.aioskata.service;

import com.example.aioskata.dto.Customer;
import com.example.aioskata.mapper.CustomerMapper;
import com.example.aioskata.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {
    private final ICustomerRepository iCustomerRepository;
    private final CustomerMapper customerMapper;

    public List<Customer> findAll() {
        return customerMapper.toDtos(iCustomerRepository.findAll());
    }

    public Customer save(Customer customer) {
        return customerMapper.toDto(iCustomerRepository.save(customerMapper.toEntity(customer)));
    }

    public Customer update(int customerId, Customer customer) {
        customer.setCustomerId(customerId);
        return customerMapper.toDto(iCustomerRepository.update(customerMapper.toEntity(customer)));
    }

    public void delete(int customerId) {
        iCustomerRepository.deleteById(customerId);
    }
}
