package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepositDTO {
    private Long id;
    private Long customerId;
    private String fullName;
    private BigDecimal balance;
    private BigDecimal transactionAmount;
}
