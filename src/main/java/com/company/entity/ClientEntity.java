package com.company.entity;

import com.company.enums.ClientStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class ClientEntity extends BaseEntity{
    private String name, surname;

    private String phone;

    private ClientStatus status;

}
