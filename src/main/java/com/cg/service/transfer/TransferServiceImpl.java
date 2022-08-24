package com.cg.service.transfer;


import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.model.dto.TransferHistoryDTO;
import com.cg.repository.CustomerRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Transfer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public List<TransferHistoryDTO> findAllHistories() {
        return transferRepository.findAllHistories();
    }

    @Override
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public Map<String, Object> doTransfer(Transfer transfer) {

        Map<String, Object> results = new HashMap<>();

        transferRepository.save(transfer);

        customerRepository.reduceBalance(transfer.getSender().getId(), transfer.getTransactionAmount());

        customerRepository.incrementBalance(transfer.getRecipient().getId(), transfer.getTransferAmount());

        Customer sender = transfer.getSender().setBalance(transfer.getSender().getBalance().subtract(transfer.getTransactionAmount()));
        Customer recipient = transfer.getRecipient().setBalance(transfer.getRecipient().getBalance().add(transfer.getTransferAmount()));

        results.put("sender", sender.toCustomerDTO());
        results.put("recipient", recipient.toCustomerDTO());

        return results;
    }
}
