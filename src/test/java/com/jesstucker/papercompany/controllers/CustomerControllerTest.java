package com.jesstucker.papercompany.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jesstucker.papercompany.models.DTO.CustomerDTO;
import com.jesstucker.papercompany.services.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerControllerTest {

    private CustomerController controller;

    @Mock
    private CustomerService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CustomerController(service);
    }

    @Test
    public void getCustomers() {
        UUID id1 = UUID.randomUUID();
        CustomerDTO expectedCustomer1 = new CustomerDTO();
        expectedCustomer1.setId(id1);
        expectedCustomer1.setFirstName("John");
        expectedCustomer1.setLastName("Doe");
        expectedCustomer1.setEmailAddress("john@doe.com");
        UUID id2 = UUID.randomUUID();
        CustomerDTO expectedCustomer2 = new CustomerDTO();
        expectedCustomer2.setId(id2);
        expectedCustomer2.setFirstName("Jane");
        expectedCustomer2.setLastName("Smith");
        expectedCustomer2.setEmailAddress("jane@smith.com");

        List<CustomerDTO> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(expectedCustomer1);
        expectedCustomers.add(expectedCustomer2);

        Mockito.when(service.findAll())
                .thenReturn(expectedCustomers);

        ResponseEntity<List<CustomerDTO>> response = controller.getCustomers();
        List<CustomerDTO> foundCustomers = response.getBody();

        assertEquals(foundCustomers, expectedCustomers);
    }

    @Test
    public void getCustomerExists() {
        UUID id = UUID.randomUUID();
        CustomerDTO expectedCustomer = new CustomerDTO();
        expectedCustomer.setId(id);
        expectedCustomer.setFirstName("John");
        expectedCustomer.setLastName("Doe");
        expectedCustomer.setEmailAddress("john@doe.com");

        Mockito.when(service.findById(Mockito.any()))
                .thenReturn(expectedCustomer);

        ResponseEntity<CustomerDTO> response = controller.getCustomer(id);
        CustomerDTO foundCustomer = response.getBody();

        assertEquals(foundCustomer, expectedCustomer);
    }

    @Test
    public void getCustomerDoesntExist() {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException()).when(service).findById(Mockito.any());

        ResponseEntity<CustomerDTO> response = controller.getCustomer(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createCustomer() {
        UUID id = UUID.randomUUID();
        CustomerDTO expectedCustomer = new CustomerDTO();
        expectedCustomer.setId(id);
        expectedCustomer.setFirstName("John");
        expectedCustomer.setLastName("Doe");
        expectedCustomer.setEmailAddress("john@doe.com");

        Mockito.when(service.create(Mockito.any()))
                .thenReturn(expectedCustomer);

        ResponseEntity<CustomerDTO> response = controller.createCustomer(expectedCustomer);
        CustomerDTO foundCustomer = response.getBody();

        assertEquals(foundCustomer, expectedCustomer);
    }

    @Test
    public void updateCustomerExists() {
        UUID id = UUID.randomUUID();
        CustomerDTO originalCustomer = new CustomerDTO();
        originalCustomer.setFirstName("John");
        originalCustomer.setLastName("Doe");
        originalCustomer.setEmailAddress("john@doe.com");
        CustomerDTO expectedCustomer = new CustomerDTO();
        expectedCustomer.setId(id);
        expectedCustomer.setFirstName("JohnChanged");
        expectedCustomer.setLastName("DoeChanged");
        expectedCustomer.setEmailAddress("johnChanged@doe.com");

        Mockito.when(service.update(id, originalCustomer))
                .thenReturn(expectedCustomer);

        ResponseEntity<CustomerDTO> response = controller.updateCustomer(id, originalCustomer);
        CustomerDTO foundCustomer = response.getBody();

        assertEquals(foundCustomer, expectedCustomer);
    }

    @Test
    public void updateCustomerDoesntExist() {
        UUID id = UUID.randomUUID();
        CustomerDTO doesntExistCustomer = new CustomerDTO();
        doesntExistCustomer.setFirstName("John");
        doesntExistCustomer.setLastName("Doe");
        doesntExistCustomer.setEmailAddress("john@doe.com");

        Mockito.doThrow(new EntityNotFoundException()).when(service).update(Mockito.any(), Mockito.any());

        ResponseEntity<CustomerDTO> response = controller.updateCustomer(id, doesntExistCustomer);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCustomerExists() {
        UUID id = UUID.randomUUID();

        ResponseEntity<CustomerDTO> response = controller.deleteCustomer(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteCustomerDoesntExist() {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(service).deleteById(Mockito.any());

        ResponseEntity<CustomerDTO> response = controller.deleteCustomer(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
