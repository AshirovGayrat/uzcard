package com.company.controller;

import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.dto.dtoRequest.CardFilterDTO;
import com.company.dto.dtoResponce.CardResponceDTO;
import com.company.service.CardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @ApiOperation(value = "create", notes = "Method for create card", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PostMapping(value = "/{id}")
    public ResponseEntity<CardResponceDTO> create(@PathVariable("id")String id,
                                                   Principal principal){
        return ResponseEntity.ok(cardService.create(id, principal));
    }

    @ApiOperation(value = "change", notes = "Method for change card status", nickname = "Dev")
    @PreAuthorize("hasAnyRole('ROLE_BANK','ROLE_ADMIN')")
    @PutMapping("/change/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")String id,
                                          @RequestBody ChangeStatusDTO dto){
        log.info("Card change status: {}", dto);
        return ResponseEntity.ok(cardService.changeStatus(id, dto));
    }

    @ApiOperation(value = "assignPhone", notes = "Method for assignPhone card", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PutMapping("/{id}")
    public ResponseEntity<?> assignPhone(@PathVariable("id")String id,
                                    @RequestBody @Valid ChangePhoneDTO dto){
        log.info("Card assignPhone: {}", dto);
        return ResponseEntity.ok(cardService.assignPhone(id, dto));
    }

    @ApiOperation(value = "getById", notes = "Method for get card by id", nickname = "Dev")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT','ROLE_BANK')")
    @GetMapping("/get-card/{id}")
    public ResponseEntity<?> getCardById(@PathVariable("id")String id){
        return ResponseEntity.ok(cardService.getDtoById(id));
    }

    @ApiOperation(value = "getById", notes = "Method for get card by id", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/number/{number}")
    public ResponseEntity<?> getCardByNumber(@PathVariable("number")String number){
        return ResponseEntity.ok(cardService.getDtoByNumber(number));
    }

    @ApiOperation(value = "getById", notes = "Method for get card by id", nickname = "Dev")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT','ROLE_BANK')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCardListByClientId(@PathVariable("id")String id){
        return ResponseEntity.ok(cardService.getCardListByClientId(id));
    }

    @ApiOperation(value = "getById", notes = "Method for get card by id", nickname = "Dev")
    @PreAuthorize("hasAnyRole('ROLE_BANK','ROLE_CLIENT')")
    @GetMapping("/balance/{number}")
    public ResponseEntity<Long> getBalanceByNumber(@PathVariable("number")String number){
        return ResponseEntity.ok(cardService.getBalanceByNumber(number));
    }

    @ApiOperation(value = "filter", notes = "Method for get Cards by filter(phone,clientId,cardId) ", nickname = "Mazgi")
    @PreAuthorize("hasAnyRole('ROLE_BANK','ROLE_ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody CardFilterDTO filterDTO) {
        log.info("Card filter: {}", filterDTO);
        return ResponseEntity.ok(cardService.filter(filterDTO));
    }

}
