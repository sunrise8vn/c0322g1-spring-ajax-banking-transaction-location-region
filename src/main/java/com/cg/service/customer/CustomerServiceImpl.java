package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import com.cg.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDTO> findAllCustomerDTO() {
        return customerRepository.findAllCustomerDTO();
    }

    @Override
    public List<Customer> findByIdIsNot(Long id) {
        return customerRepository.findByIdIsNot(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
