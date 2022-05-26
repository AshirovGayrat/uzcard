package com.company.controller;

import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.dto.dtoRequest.ClientRequestDTO;
import com.company.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "create", notes = "Method for create client", nickname = "Dev")
    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestBody @Valid ClientRequestDTO dto){
        log.info("Client create: {}", dto);
        return ResponseEntity.ok(clientService.create(dto));
    }

    @ApiOperation(value = "update", notes = "Method for update client", nickname = "Dev")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id,
                                    @RequestBody @Valid ClientRequestDTO dto){
        log.info("Client update: {}", dto);
        return ResponseEntity.ok(clientService.update(id, dto));
    }

    @ApiOperation(value = "change", notes = "Method for change client status", nickname = "Dev")
    @PutMapping("/change/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")String id,
                                          @RequestBody ChangeStatusDTO dto){
        return ResponseEntity.ok(clientService.changeStatus(id, dto));
    }

    @ApiOperation(value = "change", notes = "Method for change client status", nickname = "Dev")
    @PutMapping("/change/phone/{id}")
    public ResponseEntity<?> changePhone(@PathVariable("id")String id,
                                          @RequestBody @Valid ChangePhoneDTO dto){
        log.info("Client changePhone: {}", dto);
        return ResponseEntity.ok(clientService.changePhone(id, dto));
    }

    @ApiOperation(value = "delete", notes = "Method for delete client", nickname = "Dev")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        return ResponseEntity.ok(clientService.delete(id));
    }

    /*ADMIN*/
    @ApiOperation(value = "getAll", notes = "Method for get All clients", nickname = "Mazgi")
    @GetMapping("/pagination")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(clientService.getAllWithPagination(page, size));
    }

    @ApiOperation(value = "getById", notes = "Method for get clients by id", nickname = "Mazgi")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id")String id){
        return ResponseEntity.ok(clientService.getDtoById(id));
    }

}
