package com.example.aioskata.util;

import com.example.aioskata.entity.CustomerEntity;
import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.repository.AiosKataDb;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class DbUtil {
    public static void initDb() {
        AiosKataDb.customerTable.clear();
        AiosKataDb.purchaseOrderTable.clear();
        AiosKataDb.purchaseOrderTable.addAll(getPurchaseOrderEntities());
        AiosKataDb.customerTable.addAll(getCustomerEntities());
        AiosKataDb.customerIdCounter = 3;
        AiosKataDb.purchaseOrderIdCounter = 3;
    }

    public static List<PurchaseOrderEntity> getPurchaseOrderEntities() {
        return List.of(PurchaseOrderEntity.builder()
                        .purchaseOrderId(1)
                        .price(62.5F)
                        .deliveryDate(LocalDate.now().plusWeeks(1))
                        .quantity(25)
                        .creationDate(LocalDate.now())
                        .customerId(1)
                .build(), PurchaseOrderEntity.builder()
                        .purchaseOrderId(2)
                        .price(125)
                        .deliveryDate(LocalDate.now().plusWeeks(2))
                        .quantity(50)
                        .creationDate(LocalDate.now())
                        .customerId(1)
                .build(), PurchaseOrderEntity.builder()
                        .purchaseOrderId(3)
                        .price(125)
                        .deliveryDate(LocalDate.now().plusWeeks(2))
                        .quantity(50)
                        .creationDate(LocalDate.now())
                        .customerId(2)
                .build());
    }

    public static Set<CustomerEntity> getCustomerEntities() {
        return Set.of(CustomerEntity.builder()
                        .customerId(1)
                        .name("Alain PARROTA")
                        .address("180 avenue d'italie")
                        .postalCode("75013")
                        .city("Paris")
                        .country("France")
                .build(), CustomerEntity.builder()
                        .customerId(2)
                        .name("Benjamin FRANKLIN")
                        .address("18 avenue de saint-ouen")
                        .postalCode("75017")
                        .city("Paris")
                        .country("France")
                .build(), CustomerEntity.builder()
                        .customerId(3)
                        .name("stanislas guerini")
                        .address("35  avenue victoria")
                        .postalCode("75001")
                        .city("Paris")
                        .country("France")
                .build());
    }
}
