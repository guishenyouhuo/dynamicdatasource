package com.guigui.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Resource(name = "dynamicDataSourceSelector")
    private DataSourceSelector dynamicDataSourceSelector;

    @Override
    protected Object determineCurrentLookupKey() {
        return dynamicDataSourceSelector.getRouteKey();
    }
}
