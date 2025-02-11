package com.example.idus.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = isCurrentTransactionReadOnly() ? "SLAVE" : "MASTER";
        System.out.println("dataSourceName : " + dataSourceName);
        return dataSourceName;
    }
}
