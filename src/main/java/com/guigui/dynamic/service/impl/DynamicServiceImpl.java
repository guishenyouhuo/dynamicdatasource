package com.guigui.dynamic.service.impl;

import com.guigui.datasource.DataSourceSelector;
import com.guigui.dynamic.dao.DynamicMapper;
import com.guigui.dynamic.service.IDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dynamicServiceImpl")
public class DynamicServiceImpl implements IDynamicService {
    @Resource(name = "dynamicDataSourceSelector")
    private DataSourceSelector dynamicDataSourceSelector;
    @Autowired
    private DynamicMapper dynamicMapper;
    @Override
    public void dynamicRouting(String routingKey, String tableName, String schema) {
        // 路由数据源
        System.out.println("路由到数据源：" + routingKey);
        dynamicDataSourceSelector.setRouteKey(routingKey);
        // 从当前数据源中进行查找
        System.out.println("显示数据源 " + routingKey + "的表： " + schema + "." + tableName + " 字段列表：");
        List<String> colnums = dynamicMapper.selectAllColumns(schema, tableName);
        // 打印字段列表
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < colnums.size(); i++) {
            sb.append(colnums.get(i)).append(",");
            if (i == colnums.size() - 1) {
                sb.delete(sb.length() - 1, sb.length());
                sb.append("]");
            }
        }
        System.out.println(sb.toString());
        System.out.println();
    }

}
