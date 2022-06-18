package com.company.dto.dtoRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientRequestDTO {
    @NotNull
    @Size(min = 3, max = 25, message = "name or surname not valid")
    private String name, surname;

    @NotNull
    @Size(min = 13, max = 13, message = "Phone not valid")
    private String phone;
}
