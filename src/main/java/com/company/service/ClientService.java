package com.company.service;

import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.dto.dtoRequest.ClientRequestDTO;
import com.company.dto.dtoResponce.ClientResponceDTO;
import com.company.entity.ClientEntity;
import com.company.enums.Status;
import com.company.exp.ItemAlreadyExistsException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientResponceDTO create(ClientRequestDTO dto) {
//        if (getActiveByPhone(dto.getPhone()) != null) {
//            log.info("Client create: {}", dto);
//            throw new ItemAlreadyExistsException("Phone already exists!");
//        }
        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setStatus(Status.ACTIVE);
        clientRepository.save(entity);
        return toDto(entity);
    }

    public ClientResponceDTO update(String id, ClientRequestDTO dto) {
        ClientEntity entity = getActiveById(id);
        if (getActiveByPhone(dto.getPhone()) != null) {
            log.info("Client create: {}", dto);
            throw new ItemAlreadyExistsException("Phone already exists!");
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        clientRepository.save(entity);
        return toDto(entity);
    }

    public Boolean changeStatus(String id, ChangeStatusDTO dto) {
        getActiveById(id);
        int n = clientRepository.updateClientStatus(Status.valueOf(dto.getStatus()), id);
        return n > 0;
    }

    public Boolean changePhone(String id, ChangePhoneDTO dto) {
        getActiveById(id);
        int n = clientRepository.updateClientPhone(dto.getPhone(), id);
        return n > 0;
    }

    public Boolean delete(String id) {
        getActiveById(id);
        int n = clientRepository.updateClientStatus(Status.DELETED, id);
        return n > 0;
    }

    public ClientEntity getActiveById(String id) {
        return clientRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ItemNotFoundException("Client does not exists"));
    }

    public ClientEntity getActiveByPhone(String phone) {
        return clientRepository.findByPhoneAndStatus(phone, Status.ACTIVE)
                .orElseThrow(() -> new ItemNotFoundException("Client does not exists"));
    }

    public ClientResponceDTO toDto(ClientEntity entity) {
        ClientResponceDTO dto = new ClientResponceDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    //ADMIN//
    public ClientResponceDTO getDtoById(String id){
        return toDto(getActiveById(id));
    }

    public PageImpl<ClientResponceDTO> getAllWithPagination(int page, int size) {
        Pageable pageable= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<ClientEntity> clientPage = clientRepository.findAll(pageable);

        List<ClientEntity> playlistEntityList = clientPage.getContent();
        long totalContent = clientPage.getTotalElements();
        List<ClientResponceDTO> dtoList = playlistEntityList.stream().map(this::toDto).toList();
        return new PageImpl<ClientResponceDTO>(dtoList, pageable, totalContent);
    }
}
