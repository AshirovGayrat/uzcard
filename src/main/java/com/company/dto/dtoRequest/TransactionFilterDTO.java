package com.company.dto.dtoRequest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionFilterDTO {
    private String clientId;
    private String cardId;
    private String phone;

    private Long fromAmount;
    private Long toAmount;

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String profileName;
}
