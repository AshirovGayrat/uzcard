package com.company.repository;

import com.company.entity.ClientEntity;
import com.company.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findByIdAndStatus(String id, Status status);

    Optional<ClientEntity> findByPhoneAndStatus(String phone, Status status);

    @Transactional
    @Modifying
    @Query("update ClientEntity set status=:status where id =:id")
    int updateClientStatus(@Param("status") Status status, @Param("id")String id);

    @Transactional
    @Modifying
    @Query("update ClientEntity set phone=:phone where id =:id")
    int updateClientPhone(@Param("phone") String phone, @Param("id")String id);
}