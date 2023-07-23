package com.example.aioskata.repository;

import com.example.aioskata.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface ICustomerRepository {

    Set<CustomerEntity> findAll();

    CustomerEntity findById(int customerId);
    CustomerEntity update(CustomerEntity customer);
    boolean deleteById(int customerId);
    CustomerEntity save(CustomerEntity customer);

}
