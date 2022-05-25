package com.company.dto;

import com.company.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO implements Serializable {

    private Long cardNumber;

    private LocalDate expiredDate;

    private Long balance = 0L;

    private String clientId;

    private ClientDTO client;

    private EntityStatus status;

    private Boolean visible;

}
