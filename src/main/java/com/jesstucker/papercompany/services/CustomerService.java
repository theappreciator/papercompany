package com.jesstucker.papercompany.services;

import com.jesstucker.papercompany.models.DAO.CustomerDAO;
import com.jesstucker.papercompany.models.DTO.CustomerDTO;
import com.jesstucker.papercompany.repositories.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<CustomerDTO> findAll() {
        return repository
                .findAll()
                .stream()
                .map(this::convertCustomerDAOtoDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO findById(UUID id) {
        return convertCustomerDAOtoDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException()));

    }

    public CustomerDTO create(CustomerDTO dto) {
        CustomerDAO dao = repository.save(convertCustomerDTOtoDAO(dto));

        return convertCustomerDAOtoDTO(dao);
    }

    public CustomerDTO update(UUID id, CustomerDTO dto) {
        repository.save(convertCustomerDTOtoDAO(dto, id));

        return convertCustomerDAOtoDTO(repository.findById(id).orElse(null));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private CustomerDTO convertCustomerDAOtoDTO(CustomerDAO dao) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(dao.getId());
        dto.setVersion(dao.getVersion());
        dto.setFirstName(dao.getFirstName());
        dto.setLastName(dao.getLastName());
        dto.setEmailAddress(dao.getEmailAddress());

        return dto;
    }

    private CustomerDAO convertCustomerDTOtoDAO(CustomerDTO dto) {
        return convertCustomerDTOtoDAO(dto, null);
    }

    private CustomerDAO convertCustomerDTOtoDAO(CustomerDTO dto, UUID id) {
        CustomerDAO dao;
        if (id == null) {
            dao = new CustomerDAO();

            dao.setFirstName(dto.getFirstName());
            dao.setLastName(dto.getLastName());
            dao.setEmailAddress(dto.getEmailAddress());
        }
        else {
            // don't update the id or version from the input data
            dao = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException());

            // don't allow updates to blank out existing data
            // also don't do an update if the values are the same
            if (!StringUtils.isBlank(dto.getFirstName()) &&
                    !(dao.getFirstName().equals(dto.getFirstName()))) {
                dao.setFirstName(dto.getFirstName());
            }

            if (!StringUtils.isBlank(dto.getLastName()) &&
                    !(dao.getLastName().equals(dto.getLastName()))) {
                dao.setLastName(dto.getLastName());
            }

            if (!StringUtils.isBlank(dto.getEmailAddress()) &&
                    !(dao.getEmailAddress().equals(dto.getEmailAddress()))) {
                dao.setEmailAddress(dto.getEmailAddress());
            }
        }

        return dao;
    }
}
