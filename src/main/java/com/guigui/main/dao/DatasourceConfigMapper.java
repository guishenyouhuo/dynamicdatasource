package com.guigui.main.dao;

import com.guigui.main.models.DatasourceConfig;

import java.util.List;

public interface DatasourceConfigMapper {
    int deleteByPrimaryKey(String beanId);

    int insert(DatasourceConfig record);

    int insertSelective(DatasourceConfig record);

    DatasourceConfig selectByPrimaryKey(String beanId);

    int updateByPrimaryKeySelective(DatasourceConfig record);

    int updateByPrimaryKey(DatasourceConfig record);

    List<DatasourceConfig> selectAllDataSource();
}