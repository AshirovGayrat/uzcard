package com.company.controller;

import com.company.dto.dtoRequest.ChangePhoneDTO;
import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.dto.dtoRequest.ClientRequestDTO;
import com.company.dto.dtoRequest.ClientUpdateDTO;
import com.company.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "create", notes = "Method for create client", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid ClientRequestDTO dto,
                                    Principal principal){
        log.info("Client create: {}", dto);
        return ResponseEntity.ok(clientService.create(dto, principal));
    }

    @ApiOperation(value = "update", notes = "Method for update client", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id,
                                    @RequestBody @Valid ClientUpdateDTO dto){
        log.info("Client update: {}", dto);
        return ResponseEntity.ok(clientService.update(id, dto));
    }

    @ApiOperation(value = "change", notes = "Method for change client status", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PutMapping("/change/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")String id,
                                          @RequestBody ChangeStatusDTO dto){
        return ResponseEntity.ok(clientService.changeStatus(id, dto));
    }

    @ApiOperation(value = "change", notes = "Method for change client status", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PutMapping("/change/phone/{id}")
    public ResponseEntity<?> changePhone(@PathVariable("id")String id,
                                          @RequestBody @Valid ChangePhoneDTO dto){
        log.info("Client changePhone: {}", dto);
        return ResponseEntity.ok(clientService.changePhone(id, dto));
    }

    @ApiOperation(value = "delete", notes = "Method for delete client", nickname = "Dev")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        return ResponseEntity.ok(clientService.delete(id));
    }

    /*ADMIN*/
    @ApiOperation(value = "getAll", notes = "Method for get All clients", nickname = "Mazgi")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @GetMapping("/pagination")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(clientService.getAllWithPagination(page, size));
    }

    @ApiOperation(value = "getById", notes = "Method for get clients by id", nickname = "Mazgi")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id")String id){
        return ResponseEntity.ok(clientService.getDtoById(id));
    }

}
