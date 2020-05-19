# 动态切换数据源

### spring-jdbc 提供的 AbstractRoutingDataSource
````java
// org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

public class DynamicDataSource extends AbstractRoutingDataSource {

    // 设置一个默认的数据源，并添加一个包含数据源的 Map
    // determineCurrentLookupKey() 确定 key 之后就在该 Map 中取
	public DynamicDataSource(DataSource defaultTargetDataSource,Map<Object,Object> targetDataSources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataSources);
	}

    // 每次使用的数据库 key 
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceNameUtil.getDataSourceName();
	}


}

````
> 特别注意: 如果使用高版本的 **mybatis** 需要手动提供一个 SqlSessionFactory

````java
// 本项目中添加的方法，请酌情使用
	@Bean
	public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		return sessionFactory.getObject();
	}

````

* [个人博客](https://www.sxkawzp.cn)

