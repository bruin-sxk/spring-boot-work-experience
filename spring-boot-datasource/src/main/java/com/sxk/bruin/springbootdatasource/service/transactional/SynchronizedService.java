package com.sxk.bruin.springbootdatasource.service.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynchronizedService {

	@Autowired
	SyncTransactionalService syncTransactionalService;

	public synchronized void setCity2() {
		syncTransactionalService.setCity2();
	}

}
