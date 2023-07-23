package com.example.aioskata.mapper;
import com.example.aioskata.dto.PurchaseOrder;
import com.example.aioskata.dto.PurchaseOrderToSave;
import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.CollectionMappingStrategy.TARGET_IMMUTABLE;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = TARGET_IMMUTABLE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = CustomerMapper.class)
public interface PurchaseOrderMapper {
        PurchaseOrder toPurchaseOrder(PurchaseOrderEntity purchaseOrder, CustomerEntity customer);

        default List<PurchaseOrder> toPurchaseOrders(List<PurchaseOrderEntity> purchaseOrderEntities, CustomerEntity customer) {
                return purchaseOrderEntities.stream().map(purchaseOrder -> toPurchaseOrder(purchaseOrder, customer)).toList();
        }

        default PurchaseOrderEntity toPurchaseOrderEntity(PurchaseOrderToSave purchaseOrderToSave, float price) {
                return PurchaseOrderEntity.builder()
                        .customerId(purchaseOrderToSave.getCustomerId())
                        .deliveryDate(purchaseOrderToSave.getDeliveryDate())
                        .price(price)
                        .quantity(purchaseOrderToSave.getQuantity())
                        .build();
        }
}
