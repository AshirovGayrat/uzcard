package com.company.dto.dtoRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePhoneDTO {
    @NotNull
    @Size(min = 13, max = 13)
    private String phone;
}
