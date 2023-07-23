package com.example.aioskata.controller;

import com.example.aioskata.api.PurchaseOrderApi;
import com.example.aioskata.dto.PurchaseOrder;
import com.example.aioskata.dto.PurchaseOrderToSave;
import com.example.aioskata.service.PurchaseOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PurchaseOrderController implements PurchaseOrderApi {
    private final PurchaseOrderService purchaseOrderService;
    @Override
    public List<PurchaseOrder> getCustomerPurchaseOrders(Integer customerId) {
        return purchaseOrderService.findAllByCustomerId(customerId);
    }

    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrderToSave purchaseOrder) {
        return purchaseOrderService.save(purchaseOrder);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(int purchaseOrderId, PurchaseOrderToSave purchaseOrder) {
        return purchaseOrderService.update(purchaseOrder, purchaseOrderId);
    }

    @Override
    public ResponseEntity<Void> deletePurchaseOrder(int purchaseOrderId) {
        purchaseOrderService.deleteById(purchaseOrderId);
        return ResponseEntity.noContent().build();
    }
}
