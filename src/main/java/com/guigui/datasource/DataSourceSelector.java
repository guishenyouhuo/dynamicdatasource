package com.guigui.datasource;

public class DataSourceSelector {
	
	private static ThreadLocal<String> localRouteKey = new ThreadLocal<>();
	public void setRouteKey(String routeKey){
		localRouteKey.set(routeKey);
	}
	
	public String getRouteKey(){
		return localRouteKey.get();
	}

}
