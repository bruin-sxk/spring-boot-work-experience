package com.sxk.bruin.springbootdatasource.dao;

import com.sxk.bruin.springbootdatasource.po.City;

import java.util.List;

public interface CityDao {

	List<City> selectAll();

	int insert(City city);

}
