package com.company.dto.dtoRequest;

import com.company.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardFilterDTO {
    private String clientId;
    private String cardNumber;
    private String cardId;

    private Long fromBalance;
    private Long toBalance;

    private String profileName;

    private Status status;
}
