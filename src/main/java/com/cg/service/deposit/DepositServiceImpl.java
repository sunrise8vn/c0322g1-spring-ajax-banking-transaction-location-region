package com.cg.service.deposit;


import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Deposit> findAll() {
        return null;
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public Customer deposit(Deposit deposit) {
        deposit.setId(0L);
        depositRepository.save(deposit);

        Customer customer = deposit.getCustomer();
        customerRepository.incrementBalance(customer.getId(), deposit.getTransactionAmount());
        customer.setBalance(customer.getBalance().add(deposit.getTransactionAmount()));

        return customer;
    }
}
