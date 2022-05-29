package com.company.dto;

import com.company.enums.TransactionsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionFilterDTO {

    private String clientId;

    private String cardNumber;

    private Long fromAmount;

    private Long toAmount;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String profileName;

    private TransactionsStatus status;

}
