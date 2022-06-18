package com.company.repository.custom;

import com.company.dto.dtoRequest.TransactionFilterDTO;
import com.company.dto.dtoResponce.TransactionFilterResponceDTO;
import com.company.dto.dtoResponce.TransactionResponceDTO;
import com.company.mapper.TransactionFilterMapper;
import com.company.mapper.TransactionInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionCustomRepository {
    private final EntityManager entityManager;

    public List<TransactionResponceDTO> filterJpQuery(TransactionFilterDTO filter) {
        StringBuilder sql = new StringBuilder(" select t.id as id, t.fromCard as fromCard, t.toCard as toCard, " +
                " t.amount as amount, t.createdDate as time " +
                " from TransactionEntity as t " +
                " INNER JOIN CardEntity as c on t.fromCardNum=c.number " +
                " inner join ClientEntity as cl on c.clientId=cl.id " +
                " where t.status='SUCCESS' ");

        if (filter.getPhone() != null) {
            sql.append(" AND c.phone = '" + filter.getPhone() + "'");
        }

        if (filter.getCardId() != null) {
            sql.append(" AND  c.id = '" + filter.getCardId() + "'");
        }

        if (filter.getClientId() != null) {
            sql.append(" AND  c.clientId = '" + filter.getClientId() + "'");
        }

        Query query = entityManager.createQuery(sql.toString(), TransactionInfoMapper.class);
        List<TransactionInfoMapper> transactionList = query.getResultList();

        List<TransactionResponceDTO> dtoList = new ArrayList<>();
        transactionList.forEach(transaction -> {
            TransactionResponceDTO dto = new TransactionResponceDTO();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());
            dto.setToCard(transaction.getToCard());
            dto.setFromCard(transaction.getFromCard());
            dto.setCreatedDate(transaction.getTime());
            dtoList.add(dto);
        });

        return dtoList;
    }

    public List<TransactionFilterResponceDTO> filter(TransactionFilterDTO filter) {
        StringBuilder builder = new StringBuilder();
        builder.append(" select t.id as id, t.from_card as fromCard, t.to_card as toCard," +
                " ce.name as from_name, to_client.name as to_name," +
                " t.amount as amount, t.created_date as date" +
                " from transactions t inner join card_table c on t.from_card=c.number" +
                " inner join client_entity ce on c.client_id = ce.id" +
                " inner join card_table to_card on t.to_card=to_card.number " +
                " inner join client_entity to_client on to_card.client_id=to_client.id ");

        if (filter.getClientId() != null) {
            builder.append(" where c.client_id='" + filter.getClientId() + "' and to_card.client_id='" + filter.getClientId() + "'");
        } else {
            builder.append(" where t.status='SUCCESS' ");
        }
        if (filter.getCardId() != null) {
            builder.append(" and c.id='" + filter.getCardId() + "' and to_card.id='" + filter.getCardId() + "'");
        }
        if (filter.getPhone() != null) {
            builder.append(" and c.phone='" + filter.getPhone() + "' and to_card.phone='" + filter.getPhone() + "'");
        }
        if (filter.getProfileName() != null) {
            builder.append(" and t.profile='" + filter.getProfileName() + "'");
        }
        if (filter.getProfileName() != null) {
            builder.append(" and t.profile='" + filter.getProfileName() + "'");
        }
        if (filter.getFromAmount() != null && filter.getToAmount() != null) {
            builder.append(" and t.amount between " + filter.getFromAmount() + " and " + filter.getToAmount());
        }
        if (filter.getFromAmount() != null) {
            builder.append(" and t.amount > " + filter.getFromAmount());
        }
        if (filter.getToAmount() != null) {
            builder.append(" and t.amount < " + filter.getToAmount());
        }
        if (filter.getFromDate() != null && filter.getToDate() != null) {
            builder.append(" and t.created_date between " + filter.getFromDate() + " and " + filter.getToDate());
        }
        if (filter.getFromDate() != null) {
            builder.append(" and t.created_date > " + filter.getFromDate());
        }
        if (filter.getToDate() != null) {
            builder.append(" and t.created_date < " + filter.getToDate());
        }

        Query query = entityManager.createNativeQuery(builder.toString(), TransactionFilterMapper.class);
        List<TransactionFilterMapper> transactionList = query.getResultList();

        List<TransactionFilterResponceDTO> dtoList = new ArrayList<>();
        transactionList.forEach(transaction -> {
            TransactionFilterResponceDTO dto = new TransactionFilterResponceDTO();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());
            dto.setToCard(transaction.getToCard());
            dto.setFromCard(transaction.getFromCard());
            dto.setFromName(transaction.getFrom_name());
            dto.setToName(transaction.getTo_name());
            dto.setDate(transaction.getDate());
            dtoList.add(dto);
        });

        return dtoList;
    }
}
