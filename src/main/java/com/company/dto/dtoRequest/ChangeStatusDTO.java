package com.company.dto.dtoRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeStatusDTO {
    @NotNull
    private String status;
}
