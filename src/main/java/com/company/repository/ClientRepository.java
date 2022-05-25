package com.company.repository;

import com.company.entity.ClientEntity;
import com.company.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findByIdAndStatus(String id, ClientStatus status);

    Optional<ClientEntity> findByPhoneAndStatus(String phone, ClientStatus status);

    @Transactional
    @Modifying
    @Query("update ClientEntity set status=:status where id =:id")
    int updateClientStatus(@Param("status") ClientStatus status, @Param("id")String id);
}