package com.company.entity;

import com.company.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "card_table")
public class CardEntity extends BaseEntity {
    @Column(unique = true)
    private String number;

    private Long balance;
    private String phone;

    @Column(name = "client_id")
    private String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientEntity client;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "expired_date")
    private LocalDate expiredDate=LocalDate.now().plusYears(2);
}
