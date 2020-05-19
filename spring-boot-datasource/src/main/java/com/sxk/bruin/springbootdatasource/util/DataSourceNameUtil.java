package com.sxk.bruin.springbootdatasource.util;

public class DataSourceNameUtil {

	private static ThreadLocal<String> DATA_SOURCE_NAME = new ThreadLocal<>();

	public static void setDataSourceName(String name){
		DATA_SOURCE_NAME.set(name);
	}

	public static String getDataSourceName(){
		return DATA_SOURCE_NAME.get();
	}

	public static void remove(){
		DATA_SOURCE_NAME.remove();
	}

}
