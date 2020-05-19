package com.sxk.bruin.springbootdatasource.config;

import com.sxk.bruin.springbootdatasource.util.DataSourceNameUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author suxingkang
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	public DynamicDataSource(DataSource defaultTargetDataSource,Map<Object,Object> targetDataSources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataSources);
	}


	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceNameUtil.getDataSourceName();
	}


}
