package com.company.dto;

import com.company.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO extends BaseDTO {

    private String cardNumber;

    private LocalDate expiredDate;

    private Long balance;

    private String clientId;

    private ClientDTO client;

    private EntityStatus status;

    private Boolean visible;

}
