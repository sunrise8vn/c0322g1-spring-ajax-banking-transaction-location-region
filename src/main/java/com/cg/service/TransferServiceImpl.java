package com.cg.service;


import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.model.dto.TransferHistoryDTO;
import com.cg.repository.CustomerRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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
    public void doTransfer(Transfer transfer) {

        transferRepository.save(transfer);

        Customer sender = transfer.getSender();
        BigDecimal senderBalance = sender.getBalance().subtract(transfer.getTransactionAmount());
        sender.setBalance(senderBalance);
        customerRepository.save(sender);

        Customer recipient = transfer.getRecipient();
        BigDecimal recipientBalance = recipient.getBalance().add(transfer.getTransferAmount());
        recipient.setBalance(recipientBalance);
        customerRepository.save(recipient);
    }
}
