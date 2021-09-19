package com.jesstucker.papercompany.models.DAO;

import com.jesstucker.papercompany.models.BaseDAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Entity
public class CustomerDAO extends BaseDAO {

    @Id
    @GeneratedValue
    private UUID id;
    @Version
    private Long version;
    private String lastName;
    private String firstName;
    private String emailAddress;

    public CustomerDAO() {}

    public CustomerDAO(String firstName, String lastName, String emailAddress) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
