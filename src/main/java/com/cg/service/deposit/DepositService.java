package com.cg.service.deposit;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.IGeneralService;

public interface DepositService extends IGeneralService<Deposit> {

    Customer deposit(Deposit deposit);
}
