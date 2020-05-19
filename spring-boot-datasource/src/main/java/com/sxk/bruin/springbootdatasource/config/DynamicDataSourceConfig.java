package com.sxk.bruin.springbootdatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.sxk.bruin.springbootdatasource.constant.DataSourceNames;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suxingkang
 */
@Configuration
public class DynamicDataSourceConfig {


	@Bean
	@ConfigurationProperties("spring.datasource.druid.first")
	public DataSource firstDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.druid.second")
	public DataSource secondDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	public DynamicDataSource dynamicDataSource(DataSource firstDataSource, DataSource secondDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>(5);
		targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
		targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
		return new DynamicDataSource(firstDataSource, targetDataSources);
	}

	@Bean
	public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		return sessionFactory.getObject();
	}



}
