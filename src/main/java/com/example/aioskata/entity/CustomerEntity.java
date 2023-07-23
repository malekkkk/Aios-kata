package com.example.aioskata.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CustomerEntity {
    @EqualsAndHashCode.Exclude
    private int customerId;
    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String country;

}
