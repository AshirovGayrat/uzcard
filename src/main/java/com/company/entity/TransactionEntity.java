package com.company.entity;

import com.company.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "transactions_table")
public class TransactionEntity extends BaseEntity {

    @Column(name = "from_card_id")
    private String fromCardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_card_id", updatable = false, insertable = false)
    private CardEntity fromCard;

    @Column(name = "to_card_id")
    private String toCardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_card_id", updatable = false, insertable = false)
    private CardEntity toCard;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
