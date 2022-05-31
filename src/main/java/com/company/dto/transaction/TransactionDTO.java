package com.company.dto.transaction;

import com.company.dto.BaseDTO;
import com.company.dto.card.CardDTO;
import com.company.enums.TransactionsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO extends BaseDTO {

    @NotBlank(message = "FromCardNumber required")
    private String fromCardNumber;
    private CardDTO fromCard;

    @NotBlank(message = "ToCardNumber required")
    private String toCardNumber;
    private CardDTO toCard;

    @Positive(message = "Amount must be positive number")
    @NotNull(message = "Amount cannot be null")
    private Long amount;

    private String cash;

    private TransactionsStatus status;

    private String profileName;

}
