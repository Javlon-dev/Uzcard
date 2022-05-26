package com.company.dto;


import com.company.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardStatusDTO {

    @NotBlank(message = "CardNumber required")
    private String cardNumber;

    @NotNull(message = "Status not be null")
    private EntityStatus status;

}
