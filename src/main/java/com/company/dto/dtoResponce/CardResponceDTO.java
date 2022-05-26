package com.company.dto.dtoResponce;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardResponceDTO {
    private String number;
    private Long balance;
    private String phone;
    private LocalDate expiredDate;

    private String name, surname;
}
