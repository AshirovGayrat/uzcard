package com.company.entity;

import com.company.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "client_table")
public class ClientEntity extends BaseEntity{
    private String name, surname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;
}
