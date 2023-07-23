package com.example.aioskata.repository.impl;

import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.exception.AiosKataException;
import com.example.aioskata.repository.IPurchaseOrderRepository;
import com.example.aioskata.repository.AiosKataDb;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Repository
public class PurchaseOrderRepositoryImpl implements IPurchaseOrderRepository {
    @Override
    public List<PurchaseOrderEntity> findAll() {
        return AiosKataDb.purchaseOrderTable;
    }

    @Override
    public List<PurchaseOrderEntity> findAllByCustomerId(int customerId) {
        return AiosKataDb.purchaseOrderTable.stream().filter(purchaseOrder -> purchaseOrder.getCustomerId() == customerId)
                .toList();
    }

    @Override
    public PurchaseOrderEntity findById(int id) {
        return AiosKataDb.purchaseOrderTable.stream().filter(purchaseOrder -> purchaseOrder.getPurchaseOrderId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public PurchaseOrderEntity update(PurchaseOrderEntity purchaseOrder) {
        this.customerForeignKeyConstraint(purchaseOrder.getCustomerId());
        if(!this.deleteById(purchaseOrder.getPurchaseOrderId())) {
            throw new AiosKataException("there is no purchase order with the provided id: " + purchaseOrder.getPurchaseOrderId());
        }
        AiosKataDb.purchaseOrderTable.add(purchaseOrder);
        return purchaseOrder;
    }

    @Override
    public PurchaseOrderEntity save(PurchaseOrderEntity purchaseOrder) {
        this.customerForeignKeyConstraint(purchaseOrder.getCustomerId());
        purchaseOrder.setPurchaseOrderId(++AiosKataDb.purchaseOrderIdCounter);
        purchaseOrder.setCreationDate(now());
        AiosKataDb.purchaseOrderTable.add(purchaseOrder);
        return purchaseOrder;
    }

    @Override
    public boolean deleteById(int id) {
        int initialSize = AiosKataDb.purchaseOrderTable.size();
        AiosKataDb.purchaseOrderTable = AiosKataDb.purchaseOrderTable.stream()
                .filter(bananaPurchaseOrder -> bananaPurchaseOrder.getPurchaseOrderId() != id).collect(Collectors.toList());
        return initialSize != AiosKataDb.purchaseOrderTable.size();
    }

    private void customerForeignKeyConstraint(int customerId) {
        if (AiosKataDb.customerTable.stream().filter(c -> c.getCustomerId() == customerId).toList().isEmpty()) {
            throw new AiosKataException("There is no customer with the provided customerId: " + customerId);
        }
    }
}
