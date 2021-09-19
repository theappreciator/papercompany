package com.jesstucker.papercompany.database;

import com.jesstucker.papercompany.models.DAO.CustomerDAO;
import com.jesstucker.papercompany.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PrepDatabase {

    private static final Logger log = LoggerFactory.getLogger(PrepDatabase.class);

    @Bean
    CommandLineRunner seedDatabase(CustomerRepository repository) {

        // this is purely to have some data to easily validate through Postman
        // and also to help a discussion
        return args -> {
            log.info("Seed data " + repository.save(new CustomerDAO("Jim", "Halpert", "jim.h@papercompany.com")));
            log.info("Seed data " + repository.save(new CustomerDAO("Pam", "Beesly", "pam.b@papercompany.com")));
            log.info("Seed data " + repository.save(new CustomerDAO("Dwight", "Schrute", "dwight.s@papercompany.com")));
        };
    }
}