package com.company.dto.dtoResponce;

import com.company.dto.dtoRequest.TransactionDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponceDTO {
    private String id;
    private LocalDateTime createdDate;
    private String profile;
    private String name;
    private String surname;

    private String fromCard, toCard;

    private String fromCardId, toCardId;
    private Long amount;
}
