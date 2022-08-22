package com.cg.service;

import com.cg.model.Transfer;
import com.cg.model.dto.TransferHistoryDTO;

import java.util.List;

public interface TransferService extends IGeneralService<Transfer> {

    List<Transfer> findAll();

    List<TransferHistoryDTO> findAllHistories();

    void doTransfer(Transfer transfer);
}
