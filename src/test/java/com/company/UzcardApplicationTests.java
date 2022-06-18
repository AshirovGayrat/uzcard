package com.company;

import com.company.dto.dtoRequest.CardFilterDTO;
import com.company.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UzcardApplicationTests {
    @Autowired
    private CardService cardService;

    @Test
    void contextLoads() {
        CardFilterDTO cardFilterDTO=new CardFilterDTO();
    }

}
