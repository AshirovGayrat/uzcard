package com.company.service;

import com.company.dto.dtoRequest.CardFilterDTO;
import com.company.dto.dtoResponce.CardResponceDTO;
import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.entity.CardEntity;
import com.company.entity.ClientEntity;
import com.company.enums.Status;
import com.company.exp.BalanceNotEnaughException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.custom.CardCustomRepository;
import com.company.util.GenerateCardNumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final ClientService clientService;
    private final CardCustomRepository cardCustomRepository;

    public CardResponceDTO create(String clientId, Principal principal) {
        ClientEntity client = clientService.getActiveById(clientId);

        CardEntity cardEntity = new CardEntity();

        String cardNumber = getGeneratedCardNum();
        cardEntity.setNumber(cardNumber);
        cardEntity.setStatus(Status.ACTIVE);
        cardEntity.setClientId(clientId);
        cardEntity.setProfileUserName(principal.getName());
        cardRepository.save(cardEntity);

        CardResponceDTO dto = toDto(cardEntity);
        dto.setName(client.getName());
        dto.setSurname(client.getSurname());
        dto.setProfileUserName(cardEntity.getProfileUserName());
        return dto;
    }

    private String getGeneratedCardNum() {
        String cardNumber = GenerateCardNumberUtil.generateCard();
        Optional<CardEntity> optional = cardRepository.findByNumberAndStatus(cardNumber, Status.ACTIVE);
        if (optional.isPresent()) {
            getGeneratedCardNum();
        }
        return cardNumber;
    }

    public Boolean changeStatus(String id, ChangeStatusDTO dto) {
        getActiveById(id);
        int n = cardRepository.updateStatus(Status.valueOf(dto.getStatus()), id);
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

    public Boolean assignPhone(String id, ChangePhoneDTO dto) {
        CardEntity entity = getActiveById(id);
        if (entity.getPhone() != null) {
            return false;
        }
        int n = cardRepository.updatePhone(dto.getPhone(), id);
        return n > 0;
    }

    public Boolean delete(String id) {
        getActiveById(id);
        int n = cardRepository.updateStatus(Status.DELETED, id);
        return n > 0;
    }

    public Long getBalanceByNumber(String cardNumber) {
        return cardRepository.findBalanceByNumber(cardNumber, Status.ACTIVE);
    }

    public void checkBalance(String cardNumber, Long amount) {
        Long fromBalance = getBalanceByNumber(cardNumber);
        if (fromBalance < amount) {
            log.info("Balance not enough: {}", cardNumber);
            throw new BalanceNotEnaughException("Balance not enaugh");
        }
    }

    public List<CardResponceDTO> getCardListByClientId(String clientId) {
        List<CardResponceDTO> cardList = new ArrayList<>();
        cardRepository.findAllByClientId(clientId).forEach(card -> {
            cardList.add(toDto(card));
        });
        return cardList;
    }

    public CardResponceDTO getDtoById(String id) {
        return toDto(getActiveById(id));
    }

    public CardResponceDTO getDtoByNumber(String number) {
        return toDto(getActiveByNumber(number));
    }

    public CardResponceDTO toDto(CardEntity entity) {
        CardResponceDTO dto = new CardResponceDTO();
        dto.setPhone(entity.getPhone());
        dto.setBalance(entity.getBalance());
        dto.setNumber(entity.getNumber());
        dto.setExpiredDate(entity.getExpiredDate());
        return dto;
    }


    public List<CardResponceDTO> filter(CardFilterDTO cardFilterDTO) {
        return cardCustomRepository.filter(cardFilterDTO);
    }
}
