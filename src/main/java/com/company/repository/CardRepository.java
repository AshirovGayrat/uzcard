package com.company.repository;

import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import com.company.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    Optional<CardEntity> findByIdAndStatus(String id, Status status);

    Optional<CardEntity> findByNumberAndStatus(String phone, Status status);

    @Transactional
    @Modifying
    @Query("update CardEntity set status=:status where id =:id")
    int updateStatus(@Param("status") Status status, @Param("id")String id);

    @Transactional
    @Modifying
    @Query("update CardEntity set phone=:phone where id =:id")
    int updatePhone(@Param("phone") String phone, @Param("id")String id);

    @Transactional
    @Modifying
    @Query("update CardEntity set balance=:balance where id =:id")
    int updateBalance(@Param("balance") Long balance, @Param("id")String id);

    @Transactional
    @Modifying
    @Query("select c.balance from CardEntity as c where c.id =:id and c.status=:status")
    Long findBalanceByNumber(@Param("id")String id, @Param("status")Status status);

    List<CardEntity> findAllByClientId(String clientId);
}