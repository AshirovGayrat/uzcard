package com.company.dto.dtoRequest;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ClientUpdateDTO {
    @Size(min = 3, max = 25)
    private String name,surname;
    @Size(min = 13, max = 13)
    private String phone;
}
