package com.example.aioskata.repository;

import com.example.aioskata.entity.PurchaseOrderEntity;

import java.util.List;

public interface IPurchaseOrderRepository {

    List<PurchaseOrderEntity> findAll();
    List<PurchaseOrderEntity> findAllByCustomerId(int customerId);

    PurchaseOrderEntity findById(int id);

    PurchaseOrderEntity update(PurchaseOrderEntity purchaseOrder);

    PurchaseOrderEntity save(PurchaseOrderEntity purchaseOrder);

    boolean deleteById(int id);
}
