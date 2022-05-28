package com.company.dto;

import com.company.enums.TransactionsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO extends BaseDTO {

    private String fromCardNumber;

    private String toCardNumber;

    private CardDTO fromCard;

    private CardDTO toCard;

    private Long amount;

    private String cash;

    private TransactionsStatus status;

    private String profileName;

}
