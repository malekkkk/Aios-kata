package com.example.aioskata.repository.impl;

import com.example.aioskata.entity.CustomerEntity;
import com.example.aioskata.exception.AiosKataException;
import com.example.aioskata.repository.ICustomerRepository;
import com.example.aioskata.repository.AiosKataDb;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements ICustomerRepository {
    @Override
    public Set<CustomerEntity> findAll() {
        return AiosKataDb.customerTable;
    }

    @Override
    public CustomerEntity findById(int customerId) {
        return AiosKataDb.customerTable.stream().filter(customerEntity -> customerEntity.getCustomerId() == customerId)
                .findFirst().orElse(null);
    }

    @Override
    public CustomerEntity update(CustomerEntity customer) {
        uniqueCustomerConstraint(customer);
        if (!deleteCustomerById(customer.getCustomerId())) {
            throw new AiosKataException("there is no customer with the provided id: " + customer.getCustomerId());
        }
        AiosKataDb.customerTable.add(customer);
        return customer;
    }

    @Override
    public boolean deleteById(int customerId) {
        AiosKataDb.purchaseOrderTable = AiosKataDb.purchaseOrderTable.stream().filter(p -> p.getCustomerId() != customerId).collect(Collectors.toList());
        return deleteCustomerById(customerId);
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        uniqueCustomerConstraint(customer);
        customer.setCustomerId(++AiosKataDb.customerIdCounter);
        AiosKataDb.customerTable.add(customer);
        return customer;
    }

    private void uniqueCustomerConstraint(CustomerEntity customer) {
        if (AiosKataDb.customerTable.contains(customer)) {
            throw new AiosKataException("There is already a customer with the same provided informations");
        }
    }

    private boolean deleteCustomerById(int customerId) {
        int initialCustomerSize = AiosKataDb.customerTable.size();
        AiosKataDb.customerTable = AiosKataDb.customerTable.stream().filter(c -> c.getCustomerId() != customerId).collect(Collectors.toSet());
        return initialCustomerSize != AiosKataDb.customerTable.size();
    }
}
