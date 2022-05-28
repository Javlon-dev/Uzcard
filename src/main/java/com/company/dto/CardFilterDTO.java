package com.company.dto;

import com.company.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardFilterDTO {

    private String clientId;
    private String cardNumber;
    private String cardId;

    private Long fromAmount;
    private Long toAmount;
    private String profileName;
    private EntityStatus status;

}
