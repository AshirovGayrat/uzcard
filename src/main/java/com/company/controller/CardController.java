package com.company.controller;

import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.dto.dtoRequest.ClientRequestDTO;
import com.company.service.CardService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @ApiOperation(value = "generateCard", notes = "Method for generateCard card", nickname = "Dev")
    @PostMapping("/generate")
    private ResponseEntity<?> generateCard(){
        cardService.generateCard();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "create", notes = "Method for create card", nickname = "Dev")
    @PutMapping("/create/{client_id}")
    private ResponseEntity<?> create(@PathVariable("client_id")String clientId){
        return ResponseEntity.ok(cardService.create(clientId));
    }

    @ApiOperation(value = "change", notes = "Method for change card status", nickname = "Dev")
    @PutMapping("/change/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")String id,
                                          @RequestBody ChangeStatusDTO dto){
        return ResponseEntity.ok(cardService.changeStatus(id, dto));
    }

    @ApiOperation(value = "assignPhone", notes = "Method for assignPhone card", nickname = "Dev")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> assignPhone(@PathVariable("id")String id,
                                    @RequestBody @Valid ChangePhoneDTO dto){
        log.info("Card assignPhone: {}", dto);
        return ResponseEntity.ok(cardService.assignPhone(id, dto));
    }

    @ApiOperation(value = "getById", notes = "Method for get card by id", nickname = "Dev")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id")String id){
        return ResponseEntity.ok(cardService.getDtoById(id));
    }

    @ApiOperation(value = "getById", notes = "Method for get card by id", nickname = "Dev")
    @GetMapping("/get/number/{number}")
    public ResponseEntity<?> getByNumber(@PathVariable("number")String number){
        return ResponseEntity.ok(cardService.getDtoByNumber(number));
    }


}
