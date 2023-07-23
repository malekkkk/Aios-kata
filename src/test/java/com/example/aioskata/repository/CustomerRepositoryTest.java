package com.example.aioskata.repository;

import com.example.aioskata.entity.CustomerEntity;
import com.example.aioskata.exception.AiosKataException;
import com.example.aioskata.repository.impl.CustomerRepositoryImpl;
import com.example.aioskata.util.DbUtil;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerRepositoryTest {
    private final ICustomerRepository iCustomerRepository = new CustomerRepositoryImpl();

    @BeforeEach
    void setUp() {
        DbUtil.initDb();
    }

    @Test
    void should_get_all_customers() {
        // When
        Set<CustomerEntity> customerEntities = iCustomerRepository.findAll();

        // Then
         assertThat(customerEntities).hasSize(3);
         assertThat(customerEntities).isEqualTo(DbUtil.getCustomerEntities());
    }

    @Test
    void should_find_customer_by_id() {
        // Given
        int customerId = 1;

        // When
        CustomerEntity foundedCustomer = iCustomerRepository.findById(customerId);

        // Then
        assertThat(foundedCustomer).isNotNull();
        assertThat(foundedCustomer.getCustomerId()).isEqualTo(customerId);
        assertThat(foundedCustomer).isEqualTo(CustomerEntity.builder()
                    .customerId(1)
                    .name("Alain PARROTA")
                    .address("180 avenue d'italie")
                    .postalCode("75013")
                    .country("France")
                    .city("Paris")
                .build());
    }

    @Test
    void should_return_null_for_unfounded_id() {
        // Given
        int customerId = 5;

        // when
        CustomerEntity foundedCustomer = iCustomerRepository.findById(customerId);

        // Then
        assertThat(foundedCustomer).isNull();
    }

    @Test
    void should_save_customer() {
        // Given
        CustomerEntity customerToSave = CustomerEntity.builder()
                    .name("customer to save")
                    .address("23 bouvald jourdan")
                    .postalCode("75014")
                    .city("paris")
                    .country("France")
                .build();

        // When
        CustomerEntity savedCustomer = iCustomerRepository.save(customerToSave);

        // Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerId()).isEqualTo(4);
        assertThat(iCustomerRepository.findAll()).hasSize(4);
        assertThat(AiosKataDb.customerIdCounter).isEqualTo(4);
    }

    @Test
    void should_throw_exception_while_saving_duplicated_customer() {
        // Given
        CustomerEntity customerToSave = CustomerEntity.builder()
                    .customerId(1)
                    .name("Alain PARROTA")
                    .address("180 avenue d'italie")
                    .postalCode("75013")
                    .country("France")
                    .city("Paris")
                .build();

        // When
        ThrowableAssert.ThrowingCallable saving = () -> iCustomerRepository.save(customerToSave);

        // Then
        assertThatThrownBy(saving).isInstanceOf(AiosKataException.class);
        assertThat(iCustomerRepository.findAll()).hasSize(3);
        assertThat(AiosKataDb.customerIdCounter).isEqualTo(3);

    }

    @Test
    void should_update_customer() {
        // Given
        CustomerEntity customerToUpdate = CustomerEntity.builder()
                    .customerId(1)
                    .name("updated name")
                    .address("180 avenue d'italie")
                    .postalCode("75013")
                    .country("France")
                    .city("Paris")
                .build();

        // When
        CustomerEntity updatedCustomer = iCustomerRepository.update(customerToUpdate);

        // Then
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer).isEqualTo(customerToUpdate);

        assertThat(iCustomerRepository.findAll()).hasSize(3);

        CustomerEntity customerToCheck = iCustomerRepository.findById(1);
        assertThat(customerToCheck).isNotNull();
        assertThat(customerToCheck).isEqualTo(customerToUpdate);
    }

    @Test
    void should_not_update_customer_with_another_existing_customer_information() {
        // Given
        CustomerEntity customerToUpdate = CustomerEntity.builder()
                            .customerId(1)
                            .name("Benjamin FRANKLIN")
                            .address("18 avenue de saint-ouen")
                            .postalCode("75017")
                            .country("France")
                            .city("Paris")
                        .build();

        // Updating
        ThrowableAssert.ThrowingCallable updating = () -> iCustomerRepository.update(customerToUpdate);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);

        assertThat(iCustomerRepository.findAll()).hasSize(3);

        CustomerEntity customerToCheck = iCustomerRepository.findById(1);
        assertThat(customerToCheck).isNotNull();
        assertThat(customerToCheck).isNotEqualTo(customerToUpdate);

    }

    @Test
    void update_customer_should_throw_exception_with_invalid_customer_id() {
        // Given
        CustomerEntity customerToUpdate = CustomerEntity.builder()
                    .customerId(5)
                    .name("updated name")
                    .address("180 avenue d'italie")
                    .postalCode("75013")
                    .city("Paris")
                    .country("France")
                .build();

        // When
        ThrowableAssert.ThrowingCallable updating = () -> iCustomerRepository.update(customerToUpdate);

        // Then
        assertThatThrownBy(updating).isInstanceOf(AiosKataException.class);

        assertThat(iCustomerRepository.findAll()).hasSize(3);

        CustomerEntity customerToCheck = iCustomerRepository.findById(1);
        assertThat(customerToCheck).isNotNull();
        assertThat(customerToCheck).isNotEqualTo(customerToUpdate);

    }

    @Test
    void should_delete_customer() {
        // Given
        int customerId = 1;
        assertThat(iCustomerRepository.findById(customerId)).isNotNull();

        // When
        boolean isDeleted = iCustomerRepository.deleteById(customerId);

        // Then
        assertThat(isDeleted).isTrue();
        assertThat(iCustomerRepository.findById(customerId)).isNull();
        assertThat(AiosKataDb.purchaseOrderTable.stream().filter(p -> p.getCustomerId() == customerId).toList()).isEmpty();
    }

    @Test
    void should_not_delete_customer_when_id_not_founded() {
        // Given
        int customerId = 5;
        assertThat(iCustomerRepository.findById(customerId)).isNull();

        // When
        boolean isDeleted = iCustomerRepository.deleteById(customerId);

        // Then
        assertThat(isDeleted).isFalse();
    }
}
