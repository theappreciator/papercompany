package com.jesstucker.papercompany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaperCompanyApplication {

	private static final Logger log = LoggerFactory.getLogger(PaperCompanyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PaperCompanyApplication.class, args);

		log.info("Application started and ready for requests");
	}

}
