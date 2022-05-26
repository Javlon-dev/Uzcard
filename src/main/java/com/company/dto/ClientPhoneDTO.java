package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientPhoneDTO {

    @NotBlank(message = "Phone required")
    @Length(min = 13, max = 13, message = "Phone length must be 13 characters")
    private String phone;

}

