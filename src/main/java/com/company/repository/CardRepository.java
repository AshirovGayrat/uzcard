package com.company.repository;

import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import com.company.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    Optional<CardEntity> findByIdAndStatus(String id, Status status);

    Optional<CardEntity> findByNumberAndStatus(String phone, Status status);

    Optional<CardEntity> findByNumber(String phone);

    @Transactional
    @Modifying
    @Query("update CardEntity set status=:status where id =:id")
    int updateClientStatus(@Param("status") Status status, @Param("id")String id);

    @Transactional
    @Modifying
    @Query("update CardEntity set phone=:phone where id =:id")
    int updateClientPhone(@Param("phone") String phone, @Param("id")String id);

    @Query(value = "select * from card_table  where status=:status limit 1",nativeQuery = true )
    Optional<CardEntity> getNotActiveCard(@Param("status") Status status);
}