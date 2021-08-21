package com.honsoft.springbatch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleService {
	private static Logger logger = LoggerFactory.getLogger(SimpleService.class);
	
	public String updateFoo() {
		logger.info("simple service called");
		return "foooooooooooooooooo";
	}
}
