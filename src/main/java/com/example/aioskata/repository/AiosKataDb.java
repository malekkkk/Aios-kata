package com.example.aioskata.repository;

import com.example.aioskata.entity.PurchaseOrderEntity;
import com.example.aioskata.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AiosKataDb {
    public static List<PurchaseOrderEntity> purchaseOrderTable = new ArrayList<>();
    public static int purchaseOrderIdCounter = 0;

    public static Set<CustomerEntity> customerTable = new HashSet<>();
    public static int customerIdCounter = 0;

}
