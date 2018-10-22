package com.mpango.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class MpangoFarmApplication {

	private static final Logger log = LoggerFactory.getLogger(MpangoFarmApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MpangoFarmApplication.class, args);
	}
}
