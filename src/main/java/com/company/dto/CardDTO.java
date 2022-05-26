package com.company.dto;

import com.company.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CardDTO extends BaseDTO {

    private String cardNumber;

    private LocalDate expiredDate;

    private Long balance;

    private String cash;

    private String clientId;

    private ClientDTO client;

    private EntityStatus status;

    private Boolean visible;

    private ClientDTO fromClient;

    private ClientDTO toClient;

    public CardDTO(String id, String cardNumber, ClientDTO client) {
        super.id = id;
        this.cardNumber = cardNumber;
        this.client = client;
    }
}
