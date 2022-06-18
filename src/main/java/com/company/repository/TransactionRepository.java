package com.company.repository;

import com.company.entity.TransactionEntity;
import com.company.mapper.TransactionInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    Page<TransactionEntity> findAllByFromCardId(String fromCard, Pageable pageable);

    @Query("select t.id as id, c.number as fromCard, toC.number as toCard, t.amount as amount, t.createdDate as time, " +
            " cl.name as name, cl.surname as surname " +
            " from TransactionEntity as t " +
            " INNER JOIN CardEntity as c on t.fromCardId=c.id" +
            " INNER JOIN CardEntity as toC on t.toCardId=toC.id" +
            " inner join ClientEntity as cl on c.clientId=cl.id " +
            " where c.clientId=:clientId " +
            " order by t.createdDate desc ")
    List<TransactionInfoMapper> getTransactionsFromCard(@Param("clientId") String clientId);

    @Query("select t.id as id, fromC.number as fromCard, c.number as toCard, t.amount as amount, t.createdDate as time," +
            " cl.name as name, cl.surname as surname " +
            " from TransactionEntity as t " +
            " INNER JOIN CardEntity as c on t.toCardId=c.id " +
            " INNER JOIN CardEntity as fromC on t.fromCardId=fromC.id " +
            " inner join ClientEntity as cl on c.clientId=cl.id " +
            " where c.clientId=:clientId " +
            " order by t.createdDate desc ")
    List<TransactionInfoMapper> getTransactionsToCard(@Param("clientId") String clientId);
}