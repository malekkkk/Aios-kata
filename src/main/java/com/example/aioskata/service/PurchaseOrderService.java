package com.example.aioskata.service;

import com.example.aioskata.dto.PurchaseOrder;
import com.example.aioskata.dto.PurchaseOrderToSave;
import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.exception.AiosKataException;
import com.example.aioskata.mapper.PurchaseOrderMapper;
import com.example.aioskata.repository.IPurchaseOrderRepository;
import com.example.aioskata.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class PurchaseOrderService {

    private static final String INVALID_QUANTITY = """
            The provided quantity '%s' is not valid.
            Quantity should be more than 0, less or equal to 10 000 and multiple of 25
            """;
    private static final String INVALID_DELIVERY_DATE = """
            The provided delivery date %s is invalid.
            Delivery date should be more or equal then 7 days after the creation date %s
            """;

    private static final  String  UNFOUNDED_PURCHASE_ORDER = "There is no purchase order with the provided id: %s";
    private static final float KG_BANANA_PRICE = 2.5f;
    private final IPurchaseOrderRepository iPurchaseOrderRepository;
    private final ICustomerRepository iCustomerRepository;

    private final PurchaseOrderMapper purchaseOrderMapper;

    public List<PurchaseOrder> findAllByCustomerId(int customerId) {
        return purchaseOrderMapper.toPurchaseOrders(iPurchaseOrderRepository.findAllByCustomerId(customerId),
                    iCustomerRepository.findById(customerId));
    }

    public PurchaseOrder save(PurchaseOrderToSave purchaseOrderToSave) {
        this.validateBananaPurchaseOrder(purchaseOrderToSave, LocalDate.now());
        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderMapper.toPurchaseOrderEntity(purchaseOrderToSave,
                purchaseOrderToSave.getQuantity() * KG_BANANA_PRICE);
        return purchaseOrderMapper.toPurchaseOrder(iPurchaseOrderRepository.save(purchaseOrderEntity),
                iCustomerRepository.findById(purchaseOrderToSave.getCustomerId()));
    }

    public PurchaseOrder update(PurchaseOrderToSave purchaseOrderToSave, int purchaseOrderId) {
        PurchaseOrderEntity purchaseOrderEntity = iPurchaseOrderRepository.findById(purchaseOrderId);

        if (Objects.isNull(purchaseOrderEntity)) {
            throw new AiosKataException(String.format(UNFOUNDED_PURCHASE_ORDER, purchaseOrderId));
        }

        this.validateBananaPurchaseOrder(purchaseOrderToSave, purchaseOrderEntity.getCreationDate());

        PurchaseOrderEntity purchaseOrderToUpdate = purchaseOrderMapper.toPurchaseOrderEntity(purchaseOrderToSave,
                purchaseOrderToSave.getQuantity() * KG_BANANA_PRICE);
        purchaseOrderToUpdate.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderToUpdate.setCreationDate(purchaseOrderEntity.getCreationDate());

        return purchaseOrderMapper.toPurchaseOrder(iPurchaseOrderRepository.update(purchaseOrderToUpdate),
                iCustomerRepository.findById(purchaseOrderToSave.getCustomerId()));
    }

    private void validateBananaPurchaseOrder(PurchaseOrderToSave purchaseOrderToSave, LocalDate purchaseOrderCreationData) {

        if (purchaseOrderToSave.getQuantity() > 10_000 || purchaseOrderToSave.getQuantity() <= 0
                || purchaseOrderToSave.getQuantity() % 25 != 0) {
            throw new AiosKataException(String.format(INVALID_QUANTITY, purchaseOrderToSave.getQuantity()));
        }

        if (purchaseOrderToSave.getDeliveryDate().isBefore(purchaseOrderCreationData.plusDays(7))) {
            throw new AiosKataException(String.format(INVALID_DELIVERY_DATE, purchaseOrderToSave.getDeliveryDate(), purchaseOrderCreationData));
        }
    }

    public void deleteById(int purchaseId) {
        this.iPurchaseOrderRepository.deleteById(purchaseId);
    }
}
