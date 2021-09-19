package com.jesstucker.papercompany.services;

import com.jesstucker.papercompany.models.DAO.CustomerDAO;
import com.jesstucker.papercompany.models.DTO.CustomerDTO;
import com.jesstucker.papercompany.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CustomerService(repository);
    }

    @Test
    public void findAll() {

        UUID id1 = UUID.randomUUID();
        CustomerDAO dao1 = new CustomerDAO();
        dao1.setId(id1);
        dao1.setVersion(0L);
        dao1.setFirstName("John");
        dao1.setLastName("Doe");
        dao1.setEmailAddress("john@doe.com");
        UUID id2 = UUID.randomUUID();
        CustomerDAO dao2 = new CustomerDAO();
        dao2.setId(id2);
        dao2.setVersion(0L);
        dao2.setFirstName("Jane");
        dao2.setLastName("Smith");
        dao2.setEmailAddress("jane@smith.com");

        List<CustomerDAO> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(dao1);
        expectedCustomers.add(dao2);

        Mockito.when(repository.findAll())
                .thenReturn(expectedCustomers);

        List<CustomerDTO> foundCustomers = service.findAll();

        assertEquals(2, foundCustomers.size());
        assertEquals(id1, foundCustomers.get(0).getId());
        assertEquals(id2, foundCustomers.get(1).getId());
    }

    @Test
    public void findByIdExists() {
        UUID id = UUID.randomUUID();
        CustomerDAO expectedCustomer = new CustomerDAO();
        expectedCustomer.setId(id);
        expectedCustomer.setVersion(0L);
        expectedCustomer.setFirstName("John");
        expectedCustomer.setLastName("Doe");
        expectedCustomer.setEmailAddress("john@doe.com");

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(java.util.Optional.of(expectedCustomer));

        CustomerDTO foundCustomer = service.findById(id);

        assertEquals(id, foundCustomer.getId());
    }

    @Test
    public void findByIdDoesntExist() {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EntityNotFoundException()).when(repository).findById(Mockito.any());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    public void create() {
        CustomerDTO incomingCustomer = new CustomerDTO();
        incomingCustomer.setFirstName("John");
        incomingCustomer.setLastName("Doe");
        incomingCustomer.setEmailAddress("john@doe.com");

        UUID id = UUID.randomUUID();
        CustomerDAO expectedCustomer = new CustomerDAO();
        expectedCustomer.setId(id);
        expectedCustomer.setVersion(0L);
        expectedCustomer.setFirstName("John");
        expectedCustomer.setLastName("Doe");
        expectedCustomer.setEmailAddress("john@doe.com");

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(expectedCustomer);

        CustomerDTO foundCustomer = service.create(incomingCustomer);

        assertEquals(id, foundCustomer.getId());
    }

    @Test
    public void updateExists() {
        CustomerDTO incomingCustomer = new CustomerDTO();
        String firstName = "JohnChanged";
        incomingCustomer.setFirstName(firstName);
        incomingCustomer.setLastName("DoeChanged");
        incomingCustomer.setEmailAddress("johnChanged@doe.com");

        UUID id = UUID.randomUUID();
        CustomerDAO expectedCustomer = new CustomerDAO();
        expectedCustomer.setId(id);
        expectedCustomer.setVersion(0L);
        expectedCustomer.setFirstName(firstName);
        expectedCustomer.setLastName("DoeChanged");
        expectedCustomer.setEmailAddress("johnChanged@doe.com");

        Mockito.when(repository.findById(id))
                .thenReturn(java.util.Optional.of(expectedCustomer));

        CustomerDTO foundCustomer = service.update(id, incomingCustomer);

        assertEquals(id, foundCustomer.getId());
        assertEquals(firstName, foundCustomer.getFirstName());
    }

    @Test
    public void updateDoesntExist() {
        CustomerDTO incomingCustomer = new CustomerDTO();
        incomingCustomer.setFirstName("John");
        incomingCustomer.setLastName("Doe");
        incomingCustomer.setEmailAddress("john@doe.com");

        UUID id = UUID.randomUUID();
        CustomerDTO doesntExistCustomer = new CustomerDTO();
        doesntExistCustomer.setFirstName("John");
        doesntExistCustomer.setLastName("Doe");
        doesntExistCustomer.setEmailAddress("john@doe.com");

        Mockito.doThrow(new EntityNotFoundException()).when(repository).save(Mockito.any());

        assertThrows(EntityNotFoundException.class, () -> service.update(id, doesntExistCustomer));
    }

    @Test
    public void deleteByExists() {
        UUID id = UUID.randomUUID();

        assertDoesNotThrow(() -> service.deleteById(id));
    }

    @Test
    public void deleteByDoesntExist() {

        UUID id = UUID.randomUUID();

        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(Mockito.any());

        assertThrows(EmptyResultDataAccessException.class, () -> service.deleteById(id));
    }
}
