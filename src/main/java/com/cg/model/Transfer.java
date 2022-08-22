package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_amount", precision = 12, scale = 0)
    private BigDecimal transferAmount;

    private float fees;

    @Column(name = "fees_amount", precision = 12, scale = 0)
    private BigDecimal feesAmount;

    @Column(name = "transaction_amount", precision = 12, scale = 0)
    private BigDecimal transactionAmount;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Customer recipient;

}
