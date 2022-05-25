package com.company.controller;

import com.company.dto.dtoRequest.ChangeStatusDTO;
import com.company.dto.dtoRequest.ClientRequestDTO;
import com.company.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "create", notes = "Method for create client", nickname = "Dev")
    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestBody ClientRequestDTO dto){
        return ResponseEntity.ok(clientService.create(dto));
    }

    @ApiOperation(value = "update", notes = "Method for update client", nickname = "Dev")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id,
                                    @RequestBody ClientRequestDTO dto){
        return ResponseEntity.ok(clientService.update(id, dto));
    }

    @ApiOperation(value = "change", notes = "Method for change client status", nickname = "Dev")
    @PutMapping("/change/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")String id,
                                          @RequestBody ChangeStatusDTO dto){
        return ResponseEntity.ok(clientService.changeStatus(id, dto));
    }

    @ApiOperation(value = "delete", notes = "Method for delete client", nickname = "Dev")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        return ResponseEntity.ok(clientService.delete(id));
    }

}
