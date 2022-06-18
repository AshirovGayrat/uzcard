package com.company.util;

import com.company.entity.CardEntity;
import com.company.enums.Status;
import com.company.repository.CardRepository;
import com.company.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RequiredArgsConstructor
public class GenerateCardNumberUtil {

    public static String generateCard() {
        int min = 100000;
        int max = 999999;
        int a = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int b = (int) Math.floor(Math.random() * (max - min + 1) + min);
        StringBuilder builder = new StringBuilder();
        builder.append("8600").append(a).append(b);
        return builder.toString();
    }

}
