package com.company.dto.dtoResponce;

import com.company.dto.dtoRequest.ClientRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientResponceDTO extends ClientRequestDTO {
    private String id;
    private LocalDateTime createdDate;
}
