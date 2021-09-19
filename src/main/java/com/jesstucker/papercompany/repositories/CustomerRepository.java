package com.jesstucker.papercompany.repositories;

import com.jesstucker.papercompany.models.DAO.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerDAO, UUID> {
    // JpaRepository provides the methods we want for free:
    // - findAll()
    // - findById()
    // - save()
    // - deleteById()

    // Also, we are using H2's in memory database for the implementation in
    // order to have state while the app is running to help discussion.  We
    // could configure a different destination db in the spring config.
}
