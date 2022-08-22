package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface CustomerService extends IGeneralService<Customer> {

    List<Customer> findByIdIsNot(Long id);

    List<CustomerDTO> findAllCustomerDTO();
}
