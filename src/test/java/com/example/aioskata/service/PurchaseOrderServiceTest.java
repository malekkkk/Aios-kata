package com.example.aioskata.service;

import com.example.aioskata.dto.Customer;
import com.example.aioskata.dto.PurchaseOrder;
import com.example.aioskata.dto.PurchaseOrderToSave;
import com.example.aioskata.entity.CustomerEntity;
import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.exception.AiosKataException;
import com.example.aioskata.mapper.CustomerMapper;
import com.example.aioskata.mapper.PurchaseOrderMapper;
import com.example.aioskata.repository.ICustomerRepository;
import com.example.aioskata.repository.IPurchaseOrderRepository;
import com.example.aioskata.repository.impl.CustomerRepositoryImpl;
import com.example.aioskata.repository.impl.PurchaseOrderRepositoryImpl;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class PurchaseOrderServiceTest {

    IPurchaseOrderRepository iPurchaseOrderRepository = Mockito.mock(PurchaseOrderRepositoryImpl.class);
    ICustomerRepository iCustomerRepository = Mockito.mock(CustomerRepositoryImpl.class);

    PurchaseOrderMapper purchaseOrderMapper = Mappers.getMapper(PurchaseOrderMapper.class);
    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);
    PurchaseOrderService purchaseOrderService = new PurchaseOrderService(iPurchaseOrderRepository, iCustomerRepository, purchaseOrderMapper);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(purchaseOrderMapper, "customerMapper", customerMapper);
    }

    @Test
    void should_save_purchase_order_with_valid_information() {
        // Given
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .customerId(1)
                .deliveryDate(LocalDate.now().plusWeeks(1))
                .quantity(50)
                .build();

        // When
        when(iPurchaseOrderRepository.save(getPurchaseOrderEntityTest(0, 1, 50, null,
                LocalDate.now().plusWeeks(1)))).thenReturn(getPurchaseOrderEntityTest(1, 1,
                50, null, LocalDate.now().plusWeeks(1)));
        when(iCustomerRepository.findById(1)).thenReturn(getCustomerEntityTest(1));
            PurchaseOrder savedPurchaseOrder = purchaseOrderService.save(purchaseOrderToSave);

        // Then
        assertThat(savedPurchaseOrder).isNotNull();
        assertThat(savedPurchaseOrder).isEqualTo(getPurchaseOrderTest(1, 50,
                LocalDate.now().plusWeeks(1), getCustomerTest(1)));
    }

    @Test
    void should_not_save_purchase_order_with_delivery_date_less_than_7days_after_the_creation_date() {
        // Given
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .customerId(1)
                .deliveryDate(LocalDate.now().plusDays(6))
                .quantity(50)
                .build();

        // When
        ThrowableAssert.ThrowingCallable saving = () -> purchaseOrderService.save(purchaseOrderToSave);

        // Then
        assertThatThrownBy(saving).isInstanceOf(AiosKataException.class);
        verify(iCustomerRepository, never()).save(any());
        verify(iCustomerRepository, never()).findById(anyInt());

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10_001, 30})
    void should_not_save_purchase_order_with_invalid_quantity(int quantity) {
        // Given
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .customerId(1)
                .deliveryDate(LocalDate.now().plusWeeks(1))
                .quantity(quantity)
                .build();

        // When
        ThrowableAssert.ThrowingCallable saving = () -> purchaseOrderService.save(purchaseOrderToSave);

        // Then
        assertThatThrownBy(saving).isInstanceOf(AiosKataException.class);
        verify(iCustomerRepository, never()).save(any());
        verify(iCustomerRepository, never()).findById(anyInt());
    }

    @Test
    void should_update_purchase_order() {
        // Given
        int purchaseOrderId = 1;
        int customerId = 1;
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .quantity(50)
                .deliveryDate(LocalDate.now().plusWeeks(1))
                .customerId(customerId)
                .build();

        // When
        when(iPurchaseOrderRepository.findById(purchaseOrderId)).thenReturn(getPurchaseOrderEntityTest(purchaseOrderId, 1,
                50, LocalDate.now(),LocalDate.now().plusWeeks(2)));
        when(iPurchaseOrderRepository.update(getPurchaseOrderEntityTest(purchaseOrderId, customerId, 50,
                LocalDate.now(), LocalDate.now().plusWeeks(1)))).thenReturn(getPurchaseOrderEntityTest(purchaseOrderId, 1,
                50, LocalDate.now(),LocalDate.now().plusWeeks(1)));
        when(iCustomerRepository.findById(customerId)).thenReturn(getCustomerEntityTest(customerId));

        PurchaseOrder updatedPurchaseOrder = purchaseOrderService.update(purchaseOrderToSave, purchaseOrderId);

        // Then
        assertThat(updatedPurchaseOrder).isNotNull();
        assertThat(updatedPurchaseOrder).isEqualTo(getPurchaseOrderTest(purchaseOrderId, 50,
                LocalDate.now().plusWeeks(1), getCustomerTest(customerId)));
    }

    @Test
    void should_not_update_purchase_order_with_delivery_date_less_than_7_days_after_the_creation_date() {
        // Given
        int customerId = 1;
        int purchaseOrderId = 1;
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .deliveryDate(LocalDate.now().plusDays(6))
                .quantity(50)
                .customerId(customerId)
                .build();

        // When
        when(iPurchaseOrderRepository.findById(purchaseOrderId)).thenReturn(getPurchaseOrderEntityTest(purchaseOrderId, customerId,
                50, LocalDate.now(), LocalDate.now().plusWeeks(1)));
        ThrowableAssert.ThrowingCallable updating = () -> purchaseOrderService.update(purchaseOrderToSave, purchaseOrderId);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);
        verify(iCustomerRepository, never()).update(any());
        verify(iCustomerRepository, never()).findById(anyInt());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10_001, 30})
    void should_not_update_purchase_order_with_invalid_quantity(int quantity) {
        // Given
        int customerId = 1;
        int purchaseOrderId = 1;
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .deliveryDate(LocalDate.now().plusWeeks(2))
                .quantity(quantity)
                .customerId(customerId)
                .build();

        // When
        when(iPurchaseOrderRepository.findById(purchaseOrderId)).thenReturn(getPurchaseOrderEntityTest(purchaseOrderId, customerId,
                50, LocalDate.now(), LocalDate.now().plusWeeks(1)));
        ThrowableAssert.ThrowingCallable updating = () -> purchaseOrderService.update(purchaseOrderToSave, purchaseOrderId);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);
        verify(iCustomerRepository, never()).update(any());
        verify(iCustomerRepository, never()).findById(anyInt());
    }

    @Test
    void update_purchase_order_should_throw_exception_when_purchase_order_not_founded() {
        // Given
        int customerId = 1;
        int purchaseOrderId = 1;
        PurchaseOrderToSave purchaseOrderToSave = PurchaseOrderToSave.builder()
                .deliveryDate(LocalDate.now().plusWeeks(2))
                .quantity(50)
                .customerId(customerId)
                .build();

        // When
        when(iPurchaseOrderRepository.findById(purchaseOrderId)).thenReturn(null);
        ThrowableAssert.ThrowingCallable updating = () -> purchaseOrderService.update(purchaseOrderToSave, purchaseOrderId);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);
        verify(iCustomerRepository, never()).update(any());
        verify(iCustomerRepository, never()).findById(anyInt());
    }

    private CustomerEntity getCustomerEntityTest(int customerId) {
        return CustomerEntity.builder()
                .customerId(customerId)
                .name("Benjamin")
                .address("120 boulvard heloise")
                .country("France")
                .city("Argenteuil")
                .postalCode("95100")
                .build();
    }

    private Customer getCustomerTest(int customerId) {
        return Customer.builder()
                .customerId(customerId)
                .name("Benjamin")
                .address("120 boulvard heloise")
                .country("France")
                .city("Argenteuil")
                .postalCode("95100")
                .build();
    }

    private PurchaseOrderEntity getPurchaseOrderEntityTest(int purchaseOrderId, int customerId, int quantity, LocalDate creationDate,
                                                           LocalDate deliveryDate) {
        return PurchaseOrderEntity.builder()
                .purchaseOrderId(purchaseOrderId)
                .deliveryDate(deliveryDate)
                .creationDate(creationDate)
                .price(quantity * 2.5F)
                .quantity(quantity)
                .customerId(customerId)
                .build();
    }

    private PurchaseOrder getPurchaseOrderTest(int purchaseOrderId, int quantity, LocalDate deliveryDate, Customer customer) {
        return PurchaseOrder.builder()
                .purchaseOrderId(purchaseOrderId)
                .deliveryDate(deliveryDate)
                .price(quantity * 2.5F)
                .quantity(quantity)
                .customer(customer)
                .build();
    }


}
