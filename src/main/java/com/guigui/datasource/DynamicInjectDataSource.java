package com.guigui.datasource;

import com.guigui.main.dao.DatasourceConfigMapper;
import com.guigui.main.models.DatasourceConfig;
import com.guigui.utils.SpringContextHolder;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicInjectDataSource {

    @Autowired
    private DatasourceConfigMapper datasourceConfigMapper;

    private static final String URL_PREFIX = "jdbc:mysql://";
    private static final String URL_SURFIX = "?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull";
    private static final String DESTORY_METHOD = "close";
    private static final String DYNAMIC_DATASOURCE = "dynamicDataSource";

    public void startUp() throws Exception {
        this.dynamicInject();
    }

    private void dynamicInject() throws Exception {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextHolder.getContext();
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        ManagedMap<String, BeanDefinition> dataSourceMap = new ManagedMap<>();
        List<DatasourceConfig> dataSourceConfigList = datasourceConfigMapper.selectAllDataSource();
        if (CollectionUtils.isEmpty(dataSourceConfigList)) {
            System.out.println("未查询到相关数据源！");
            throw new Exception("初始化动态数据源失败！");
        }
        for (DatasourceConfig config : dataSourceConfigList) {
            String beanId = config.getBeanId();
            System.out.println("开始注册Mysql数据源：" + config.getDsKey());
            // 如果存在则需要重新注册，防止有修改需要刷新
            if (defaultListableBeanFactory.containsBean(beanId)) {
                defaultListableBeanFactory.removeBeanDefinition(beanId);
            }
            // 注册新的Bean
            BeanDefinitionBuilder dataSourceBuilder = BeanDefinitionBuilder.genericBeanDefinition(BasicDataSource.class);
            dataSourceBuilder.setDestroyMethodName(DESTORY_METHOD);
            dataSourceBuilder.addPropertyValue("url", URL_PREFIX + config.getUrl() + URL_SURFIX);
            dataSourceBuilder.addPropertyValue("username", config.getUserName());
            dataSourceBuilder.addPropertyValue("password", config.getPassword());
            dataSourceBuilder.addPropertyValue("maxActive", config.getMaxactive());
            defaultListableBeanFactory.registerBeanDefinition(beanId, dataSourceBuilder.getRawBeanDefinition());
            // 动态添加数据源
            dataSourceMap.put(config.getDsKey(), dataSourceBuilder.getRawBeanDefinition());
        }

        /* 重新注册动态数据源**/
        Map<String, Object> dynamicDSPropertiesMap = new HashMap<>();
        dynamicDSPropertiesMap.put("targetDataSources", dataSourceMap);
        BeanDefinition dynamicDataSourceBean = this.reRegisterBeanDefinition(DYNAMIC_DATASOURCE, dynamicDSPropertiesMap);

        /* 重新注册事务管理器**/
        Map<String, Object> dynamicDSManagerProsMap = new HashMap<>();
        dynamicDSManagerProsMap.put("dataSource", dynamicDataSourceBean);
        BeanDefinition dynamicManageBean = this.reRegisterBeanDefinition("dynamicTransactionManager", dynamicDSManagerProsMap);

        /* 重新注册Advice**/
        Map<String, Object> dynamicAdviceProsMap = new HashMap<>();
        dynamicAdviceProsMap.put("transactionManager", dynamicManageBean);
        this.reRegisterBeanDefinition("dynamicAdvice", dynamicAdviceProsMap);

        /* 重新注册Advisor**/
        Map<String, Object> dynamicAdvisorProsMap = new HashMap<>();
        dynamicAdvisorProsMap.put("adviceBeanName", "dynamicAdvice");
        this.reRegisterBeanDefinition("dynamicAdvisor", dynamicAdvisorProsMap);

    }

    /**
     * 重新注册Bean通用方法
     *
     * @param beanName   bean名称
     * @param properties 属性
     */
    private BeanDefinition reRegisterBeanDefinition(String beanName, Map<String, Object> properties) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextHolder.getContext();
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        BeanDefinition regBean = defaultListableBeanFactory.getBeanDefinition(beanName);
        Set<String> propertyKeys = properties.keySet();
        // 重新设置Bean的属性
        for (String propertyKey : propertyKeys) {
            regBean.getPropertyValues().removePropertyValue(propertyKey);
            regBean.getPropertyValues().add(propertyKey, properties.get(propertyKey));
        }
        // 删除原有Bean
        if (defaultListableBeanFactory.containsBean(beanName)) {
            defaultListableBeanFactory.removeBeanDefinition(beanName);
        }
        // 重新注册Bean
        defaultListableBeanFactory.registerBeanDefinition(beanName, regBean);
        return regBean;
    }
}
