package com.company.mapper;

import java.time.LocalDateTime;

public interface TransactionFilterMapper {
    String getId();
    String getFromCard();
    String getToCard();
    String getFrom_name();
    String getTo_name();
    Long getAmount();
    LocalDateTime getDate();
}
