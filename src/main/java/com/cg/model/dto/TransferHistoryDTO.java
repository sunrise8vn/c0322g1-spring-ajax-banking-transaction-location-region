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
public class TransferHistoryDTO {

    private Long id;
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;
    private BigDecimal transferAmount;
    private float fees;
    private BigDecimal feesAmount;

}
