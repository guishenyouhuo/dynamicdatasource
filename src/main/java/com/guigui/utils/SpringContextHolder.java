package com.guigui.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static volatile ApplicationContext SPRING_APPLICATION_CONTEXT = null;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SPRING_APPLICATION_CONTEXT = applicationContext;

	}
	public static ApplicationContext getContext() {
		return SPRING_APPLICATION_CONTEXT;
	}

	@Override
	public void destroy() throws Exception {
		SPRING_APPLICATION_CONTEXT = null;
	}
}
