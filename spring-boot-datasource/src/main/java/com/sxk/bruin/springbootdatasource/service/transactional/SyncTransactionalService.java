package com.sxk.bruin.springbootdatasource.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxk.bruin.springbootdatasource.dao.CityDao;
import com.sxk.bruin.springbootdatasource.po.City;

@Service
public class SyncTransactionalService {

	@Autowired
	CityDao cityDao;


	/**
	 * 错误写法
	 *
	 * 事务，是通过动态代理生效的，在执行真正的方法（同步方法）之前开启事务，执行之后提交事务，整个代理类的方法不是同步的，
	 * 就会造成，上一个线程执行了该方法，但是没提交，下一个线程先一步提交了，导致上一个线程的执行结果不再是预期结果。
	 */
	@Transactional
	public synchronized void setCity() {
		City city = new City();
		city.setCid(String.valueOf(System.currentTimeMillis()));
		city.setCname("全部都是你的");
		int insert = cityDao.insert(city);

	}


	@Transactional
	public void setCity2() {
		City city = new City();
		city.setCid(String.valueOf(System.currentTimeMillis()));
		city.setCname("全部都是你的");
		int insert = cityDao.insert(city);

	}


}
