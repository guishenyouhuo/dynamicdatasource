package com.guigui.dynamic.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicMapper {

	List<String> selectAllColumns(@Param("schema") String schema, @Param("tableName") String tableName);

	List<String> selectPrimaryKeyNames(@Param("schema") String schema, @Param("tableName") String tableName);

}
