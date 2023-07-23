package com.example.aioskata.mapper;

import com.example.aioskata.dto.Customer;
import com.example.aioskata.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

import static org.mapstruct.CollectionMappingStrategy.TARGET_IMMUTABLE;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = TARGET_IMMUTABLE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer toDto(CustomerEntity entity);

    List<Customer> toDtos(Set<CustomerEntity> entities);

    CustomerEntity toEntity(Customer dto);
}