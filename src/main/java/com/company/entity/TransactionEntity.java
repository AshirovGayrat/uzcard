package com.company.entity;

import com.company.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity {
    private String fromCard;
    private String toCard;
    private Long amount;
    private TransactionStatus status;
    private String profileId;
}
