package com.company.entity;

import com.company.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class ClientEntity extends BaseEntity{
    private String name, surname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String profileUserName;
}
