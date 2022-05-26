package com.company.service;

import com.company.dto.dtoResponce.CardResponceDTO;
import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import com.company.enums.Status;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ClientService clientService;

    public void generateCard() {
        int min = 100000;
        int max = 999999;
        int a = (int) Math.floor(Math.random() * (max - min + 1) + min);
        int b = (int) Math.floor(Math.random() * (max - min + 1) + min);
        StringBuilder builder = new StringBuilder();
        builder.append("8600").append(a).append(b);
        if (getByNumber(builder.toString()).isPresent()) {
            generateCard();
        }

        CardEntity entity = new CardEntity();
        entity.setStatus(Status.NOTACTIVE);
        entity.setNumber(builder.toString());
        entity.setBalance(0L);
        try {
            cardRepository.save(entity);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public CardResponceDTO create(String clientId){
        ClientEntity client=clientService.getActiveById(clientId);

        CardEntity cardEntity=cardRepository.getNotActiveCard(Status.NOTACTIVE)
                .orElseThrow(()-> new ItemNotFoundException("Card does not exists"));

        cardEntity.setClientId(clientId);
        cardEntity.setStatus(Status.ACTIVE);
        cardEntity.setExpiredDate(LocalDate.now().plusYears(2));
        cardEntity.setCreatedDate(LocalDateTime.now());
        cardRepository.save(cardEntity);
        return null;
    }

    public Boolean changeStatus(String id, ChangeStatusDTO dto) {
        getActiveById(id);
        int n = cardRepository.updateClientStatus(Status.valueOf(dto.getStatus()), id);
        return n > 0;
    }

    public CardEntity getActiveById(String id) {
        return cardRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ItemNotFoundException("Card does not exists"));
    }

    public CardEntity getActiveByNumber(String phone) {
        return cardRepository.findByNumberAndStatus(phone, Status.ACTIVE)
                .orElseThrow(() -> new ItemNotFoundException("Card does not exists"));
    }

    public Optional<CardEntity> getByNumber(String phone) {
        return cardRepository.findByNumber(phone);
    }

    public Boolean assignPhone(String id, ChangePhoneDTO dto) {
        CardEntity entity = getActiveById(id);
        if (entity.getPhone()!=null){
            return false;
        }
        int n = cardRepository.updateClientPhone(dto.getPhone(), id);
        return n > 0;
    }

    public Boolean delete(String id) {
        getActiveById(id);
        int n = cardRepository.updateClientStatus(Status.DELETED, id);
        return n > 0;
    }

    public CardResponceDTO getDtoById(String id){
        return toDto(getActiveById(id));
    }

    public CardResponceDTO getDtoByNumber(String number){
        return toDto(getActiveByNumber(number));
    }

    public CardResponceDTO toDto(CardEntity entity){
        CardResponceDTO dto=new CardResponceDTO();
        dto.setPhone(entity.getPhone());
        dto.setBalance(entity.getBalance());
        dto.setNumber(entity.getNumber());
        dto.setExpiredDate(entity.getExpiredDate());
        return dto;
    }
}
