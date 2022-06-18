package com.company.dto.dtoRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TransactionDTO {
    @NotNull
    @Size(min = 16, max = 16)
    private String fromCard, toCard;
    @NotNull
    private Long amount;

}
