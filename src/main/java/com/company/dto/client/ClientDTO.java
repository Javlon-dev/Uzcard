package com.company.dto.client;

import com.company.dto.BaseDTO;
import com.company.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ClientDTO extends BaseDTO {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @NotBlank(message = "Phone required")
    private String phone;

    private EntityStatus status;

    private String profileName;

    public ClientDTO(String id,String name, String surname, String phone) {
        super.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
}
