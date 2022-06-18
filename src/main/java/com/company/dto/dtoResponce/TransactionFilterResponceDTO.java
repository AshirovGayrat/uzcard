package com.company.dto.dtoResponce;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionFilterResponceDTO {
    String id;
    String fromCard;
    String toCard;
    String fromName;
    String toName;
    Long amount;
    LocalDateTime date;
}
