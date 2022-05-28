package com.company.entity;

import com.company.enums.TransactionsStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionEntity extends BaseEntity{

    @Column(name = "from_card", nullable = false)
    private String fromCard;

    @Column(name = "to_card", nullable = false)
    private String toCard;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionsStatus status;

    @Column(name = "profile_name", nullable = false)
    private String profileName;
}
