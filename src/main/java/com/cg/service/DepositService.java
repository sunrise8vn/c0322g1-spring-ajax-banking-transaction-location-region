package com.cg.service;

import com.cg.model.Customer;
import com.cg.model.Deposit;

public interface DepositService extends IGeneralService<Deposit> {

    Customer deposit(Deposit deposit);
}
