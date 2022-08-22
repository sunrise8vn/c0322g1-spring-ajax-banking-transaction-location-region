package com.cg.controller.rest;


import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.service.customer.CustomerService;
import com.cg.service.locationRegion.LocationRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LocationRegionService locationRegionService;

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomers() {

//        List<Customer> customers = customerService.findAll();

        List<CustomerDTO> customers = customerService.findAllCustomerDTO();

        if (customers.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody CustomerDTO customerDTO) {
        customerDTO.getLocationRegion().setId(0L);
        LocationRegion locationRegion = locationRegionService.save(customerDTO.getLocationRegion().toLocationRegion());

        customerDTO.setLocationRegion(locationRegion.toLocationRegionDTO());
        customerDTO.setId(0L);
        customerDTO.setBalance(new BigDecimal(0L));

        Customer customer = customerDTO.toCustomer();

//        customer.setLocationRegion(locationRegion);
        Customer newCustomer = customerService.save(customer);

        return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);
    }

}
