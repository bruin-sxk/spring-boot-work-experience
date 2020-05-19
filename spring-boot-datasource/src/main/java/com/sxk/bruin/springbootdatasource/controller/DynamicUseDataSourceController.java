package com.sxk.bruin.springbootdatasource.controller;

import com.sxk.bruin.springbootdatasource.annotation.UseFirstDataSource;
import com.sxk.bruin.springbootdatasource.dao.CityDao;
import com.sxk.bruin.springbootdatasource.dao.MenusDao;
import com.sxk.bruin.springbootdatasource.po.City;
import com.sxk.bruin.springbootdatasource.po.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DynamicUseDataSourceController {

	@Autowired
	MenusDao menusDao;

	@Autowired
	CityDao cityDao;

	@GetMapping(value = "/useFirst")
	@ResponseBody
	public String useFirst() {
		List<Menus> menus = menusDao.selectAll();
		System.out.println(menus.size());
		return "调用 first dataSource";
	}

	@GetMapping("/useSecond")
	@UseFirstDataSource
	@ResponseBody
	public String useSecond() {
		List<City> cities = cityDao.selectAll();
		System.out.println(cities.size());
		return "调用 second dataSource";
	}


}
