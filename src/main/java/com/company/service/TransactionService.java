package com.company.service;

import com.company.dto.dtoRequest.TransactionDTO;
import com.company.dto.dtoRequest.TransactionFilterDTO;
import com.company.dto.dtoResponce.TransactionFilterResponceDTO;
import com.company.dto.dtoResponce.TransactionResponceDTO;
import com.company.entity.CardEntity;
import com.company.entity.TransactionEntity;
import com.company.enums.TransactionStatus;
import com.company.mapper.TransactionInfoMapper;
import com.company.repository.CardRepository;
import com.company.repository.TransactionRepository;
import com.company.repository.custom.TransactionCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final TransactionCustomRepository transactionCustomRepository;

    @Transactional
    public Boolean create(TransactionDTO dto, Principal principal) {
        CardEntity fromCard = cardService.getActiveByNumber(dto.getFromCard());
        CardEntity toCard = cardService.getActiveByNumber(dto.getToCard());

        cardService.checkBalance(fromCard.getNumber(), dto.getAmount());//chech balance

        Long fromBalance = fromCard.getBalance() - dto.getAmount();
        Long toBalance = toCard.getBalance() + dto.getAmount();

        cardRepository.updateBalance(fromBalance, fromCard.getId());
        cardRepository.updateBalance(toBalance, toCard.getId());

        TransactionEntity entity = new TransactionEntity();
//        entity.setFromCardId(fromCard.getId());
        entity.setToCardId(toCard.getId());
        entity.setAmount(dto.getAmount());
        entity.setProfileUserName(principal.getName());
        entity.setStatus(TransactionStatus.SUCCESS);
        repository.save(entity);
        return true;
    }

//    public PageImpl<TransactionResponceDTO> paginationToDto(Page<TransactionEntity> getPage,
//                                                            Pageable pageable) {
//        List<TransactionEntity> playlistEntityList = getPage.getContent();
//        long totalContent = getPage.getTotalElements();
//        List<TransactionResponceDTO> dtoList = playlistEntityList.stream().map(this::toDto).toList();
//        return new PageImpl<TransactionResponceDTO>(dtoList, pageable, totalContent);
//    }

    public List<TransactionResponceDTO> getListByClientId(String clientId) {
        List<TransactionInfoMapper> transactionFromCardPage = repository.getTransactionsFromCard(clientId);
        List<TransactionInfoMapper> transactionToCardPage = repository.getTransactionsToCard(clientId);

        List<TransactionResponceDTO> dtoList = new ArrayList<>();

        transactionFromCardPage.forEach(transaction -> {
            TransactionResponceDTO dto = new TransactionResponceDTO();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());
            dto.setToCard(transaction.getToCard());
            dto.setFromCard(transaction.getFromCard());
            dto.setCreatedDate(transaction.getTime());
            dto.setName(transaction.getName());
            dto.setSurname(transaction.getSurname());
            dtoList.add(dto);
        });
        transactionToCardPage.forEach(transaction -> {
            TransactionResponceDTO dto = new TransactionResponceDTO();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());
            dto.setToCard(transaction.getToCard());
            dto.setFromCard(transaction.getFromCard());
            dto.setCreatedDate(transaction.getTime());
            dto.setName(transaction.getName());
            dto.setSurname(transaction.getSurname());
            dtoList.add(dto);
        });
        return dtoList;
    }

    public PageImpl<TransactionResponceDTO> getPagintionByCard(String fromCardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<TransactionEntity> transactionPage = repository.findAllByFromCardId(fromCardId, pageable);
        List<TransactionEntity> playlistEntityList = transactionPage.getContent();
        long totalContent = transactionPage.getTotalElements();
        List<TransactionResponceDTO> dtoList = playlistEntityList.stream().map(this::toDto).toList();
        return new PageImpl<TransactionResponceDTO>(dtoList, pageable, totalContent);
    }

    public List<TransactionFilterResponceDTO> filter(TransactionFilterDTO dto){
        return transactionCustomRepository.filter(dto);
    }

    public List<TransactionResponceDTO> filterJpQuery(TransactionFilterDTO dto){
        return transactionCustomRepository.filterJpQuery(dto);
    }

    public TransactionResponceDTO toDto(TransactionEntity entity) {
        TransactionResponceDTO dto = new TransactionResponceDTO();
        dto.setId(entity.getId());
//        dto.setFromCardId(entity.getFromCardId());
        dto.setToCardId(entity.getToCardId());
        dto.setAmount(entity.getAmount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfile(entity.getProfileUserName());
        return dto;
    }

}
