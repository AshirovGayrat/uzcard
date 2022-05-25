package com.company.entity;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "card_table")
public class CardEntity extends BaseEntity {

    private String number;
    private Long balance;
    @Column(name = "client_id")
    private Long clientId;
    private String phone;
    private CardStatus status;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
}
