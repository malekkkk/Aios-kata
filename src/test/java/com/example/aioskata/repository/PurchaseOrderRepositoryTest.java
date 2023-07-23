package com.example.aioskata.repository;

import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.exception.AiosKataException;
import com.example.aioskata.repository.impl.PurchaseOrderRepositoryImpl;
import com.example.aioskata.util.DbUtil;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PurchaseOrderRepositoryTest {

    private final IPurchaseOrderRepository iPurchaseOrderRepository = new PurchaseOrderRepositoryImpl();

    @BeforeEach
    void setUp() {
        DbUtil.initDb();

    }

    @Test
    void should_get_purchase_orders_by_customer_id() {
        // Given
        int customerId = 1;

        // When
        List<PurchaseOrderEntity> purchaseOrders = iPurchaseOrderRepository.findAllByCustomerId(customerId);

        // Then
        assertThat(purchaseOrders).hasSize(2);
        assertThat(purchaseOrders)
                .extracting(PurchaseOrderEntity::getCustomerId)
                .containsExactly(customerId, customerId);
    }

    @Test
    void should_save_purchase_order() {
        // Given
        int customerId = 1;
        assertThat(iPurchaseOrderRepository.findAllByCustomerId(customerId)).hasSize(2);
        PurchaseOrderEntity purchaseOrder = PurchaseOrderEntity.builder()
                .customerId(customerId)
                .price(125)
                .quantity(50)
                .deliveryDate(LocalDate.now().plusWeeks(5))
                .build();

        // When
        PurchaseOrderEntity savedPurchaseOrder = iPurchaseOrderRepository.save(purchaseOrder);

        // Then
        PurchaseOrderEntity expected = PurchaseOrderEntity.builder()
                .purchaseOrderId(4)
                .creationDate(LocalDate.now())
                .customerId(customerId)
                .price(125)
                .quantity(50)
                .deliveryDate(LocalDate.now().plusWeeks(5))
                .build();
        assertThat(savedPurchaseOrder).isNotNull();
        assertThat(savedPurchaseOrder).isEqualTo(expected);
        assertThat(iPurchaseOrderRepository.findAllByCustomerId(customerId)).hasSize(3);
    }

    @Test
    void should_not_save_purchase_order_with_unfounded_customer() {
        // Given
        int customerId = 5;
        PurchaseOrderEntity purchaseOrder = PurchaseOrderEntity.builder()
                .customerId(customerId)
                .price(125)
                .quantity(50)
                .deliveryDate(LocalDate.now().plusWeeks(5))
                .build();

        // When
        ThrowableAssert.ThrowingCallable saving = () -> iPurchaseOrderRepository.save(purchaseOrder);

        // Then
        assertThatThrownBy(saving).isInstanceOf(AiosKataException.class);
    }

    @Test
    void should_update_purchase_order() {
        // Given
        int customerId = 3;
        int purchaseOrderId = 1;
        PurchaseOrderEntity purchaseOrderEntity = PurchaseOrderEntity.builder()
                .purchaseOrderId(purchaseOrderId)
                .customerId(customerId)
                .price(250)
                .quantity(100)
                .build();

        assertThat(iPurchaseOrderRepository.findById(purchaseOrderId).getCustomerId()).isEqualTo(1);
        assertThat(iPurchaseOrderRepository.findAllByCustomerId(customerId)).isEmpty();

        // When
        PurchaseOrderEntity updatedPurchaseOrder = iPurchaseOrderRepository.update(purchaseOrderEntity);

        // Then
        assertThat(updatedPurchaseOrder).isNotNull();
        assertThat(updatedPurchaseOrder).isEqualTo(purchaseOrderEntity);
        assertThat(iPurchaseOrderRepository.findAllByCustomerId(customerId)).isNotEmpty();
        assertThat(iPurchaseOrderRepository.findById(purchaseOrderId).getCustomerId()).isEqualTo(customerId);
    }

    @Test
    void should_not_updatepurchase_order_with_invalid_id() {
        // Given
        PurchaseOrderEntity purchaseOrder = PurchaseOrderEntity.builder()
                .purchaseOrderId(5)
                .customerId(1)
                .price(125)
                .quantity(50)
                .deliveryDate(LocalDate.now().plusWeeks(5))
                .build();

        // When
        ThrowableAssert.ThrowingCallable updating = () -> iPurchaseOrderRepository.update(purchaseOrder);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);
    }

    @Test
    void should_not_update_purchase_order_with_unfounded_customer() {

        // Given
        PurchaseOrderEntity purchaseOrder = PurchaseOrderEntity.builder()
                .purchaseOrderId(1)
                .customerId(5)
                .price(125)
                .quantity(50)
                .deliveryDate(LocalDate.now().plusWeeks(5))
                .build();

        // When
        ThrowableAssert.ThrowingCallable updating = () -> iPurchaseOrderRepository.update(purchaseOrder);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);

    }

    @Test
    void should_delete_purchase_order() {
        // Given
        int purchaseOrderId = 1;
        assertThat(iPurchaseOrderRepository.findById(purchaseOrderId)).isNotNull();

        // When
        boolean deleting = iPurchaseOrderRepository.deleteById(purchaseOrderId);

        // Then
        assertThat(iPurchaseOrderRepository.findById(purchaseOrderId)).isNull();
        assertThat(deleting).isTrue();
    }
}
