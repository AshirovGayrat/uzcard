package com.company.entity;

import com.company.enums.ClientStatus;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ClientEntity extends BaseEntity{
    private String name, surname;

    private String phone;

    private ClientStatus status;

}
