package com.cg.service.transfer;

import com.cg.model.Transfer;
import com.cg.model.dto.TransferHistoryDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Map;

public interface TransferService extends IGeneralService<Transfer> {

    List<Transfer> findAll();

    List<TransferHistoryDTO> findAllHistories();

    Map<String, Object> doTransfer(Transfer transfer);
}
