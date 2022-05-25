package com.company.dto;

import com.company.enums.TransactionsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsDTO implements Serializable {

    private String fromCard;

    private String toCard;

    private Long amount;

    private TransactionsStatus status;

    private String profileName;

}
