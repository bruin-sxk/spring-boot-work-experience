package com.sxk.bruin.springbootdatasource.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxk.bruin.springbootdatasource.service.transactional.SyncTransactionalService;

public class SyncTransactionalController {

	@Autowired
	SyncTransactionalService syncTransactionalService;


	public void setCity() {
		for (int i = 0; i < 100; i++) {
			syncTransactionalService.setCity();
		}
	}

}
