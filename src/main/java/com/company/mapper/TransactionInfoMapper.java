package com.company.mapper;

import java.time.LocalDateTime;

public interface TransactionInfoMapper {
    String getId();
    String getFromCard();
    String getToCard();
    Long getAmount();
    LocalDateTime getTime();
    String getName();
    String getSurname();
}
