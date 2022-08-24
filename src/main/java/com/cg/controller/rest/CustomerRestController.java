package com.cg.controller.rest;


import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.LocationRegion;
import com.cg.model.Transfer;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.DepositDTO;
import com.cg.model.dto.TransferDTO;
import com.cg.service.transfer.TransferService;
import com.cg.service.customer.CustomerService;
import com.cg.service.deposit.DepositService;
import com.cg.service.locationRegion.LocationRegionService;
import com.cg.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LocationRegionService locationRegionService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private AppUtil appUtil;

    @GetMapping
    public ResponseEntity<List<?>> getAllCustomers() {

//        List<Customer> customers = customerService.findAll();

        List<CustomerDTO> customers = customerService.findAllCustomerDTO();

        if (customers.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new ResourceNotFoundException("Customer invalid");
        }

        return new ResponseEntity<>(customerOptional.get().toCustomerDTO(), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

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

    @PostMapping("/deposit")
    public ResponseEntity<?> doDeposit(@Validated @RequestBody DepositDTO depositDTO, BindingResult bindingResult) {

        new DepositDTO().validate(depositDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Long customerId = depositDTO.getCustomerId();

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
//            return new ResponseEntity<>("Customer invalid", HttpStatus.BAD_REQUEST);
            throw new ResourceNotFoundException("Customer invalid");
        }

        depositDTO.setId(0L);
        Deposit deposit = depositDTO.toDeposit(customerOptional.get());

        try {
            Customer newCustomer = depositService.deposit(deposit);

            return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid deposit information");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> doTransfer(@Validated @RequestBody TransferDTO transferDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Long senderId = transferDTO.getSenderId();
        Long recipientId = transferDTO.getRecipientId();

        if (senderId.equals(recipientId)) {
            throw new DataInputException("Sender ID different Recipient ID");
        }

        Optional<Customer> senderOptional = customerService.findById(senderId);

        Optional<Customer> recipientOptional = customerService.findById(recipientId);

        if (!senderOptional.isPresent()) {
            throw new ResourceNotFoundException("Invalid Sender information");
        }

        if (!recipientOptional.isPresent()) {
            throw new ResourceNotFoundException("Invalid Recipient information");
        }

        BigDecimal senderCurrentBalance = senderOptional.get().getBalance();
        BigDecimal recipientCurrentBalance = recipientOptional.get().getBalance();

        BigDecimal transferAmount = transferDTO.getTransferAmount();
        float fees = 10;

        BigDecimal feesAmount = transferAmount.multiply(new BigDecimal(fees)).divide(new BigDecimal(100L));

        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (senderCurrentBalance.compareTo(transactionAmount) < 0) {
            throw new DataInputException("Sender balance not enough money for this transaction");
        }

        Customer sender = senderOptional.get();
        Customer recipient = recipientOptional.get();

        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer.setTransferAmount(transferAmount);
        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);

        try {
            Map<String, Object> results = transferService.doTransfer(transfer);

            return new ResponseEntity<>(results, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid transfer information");
        }
    }

}
