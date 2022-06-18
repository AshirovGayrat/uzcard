package com.company.controller;

import com.company.dto.dtoRequest.TransactionDTO;
import com.company.dto.dtoRequest.TransactionFilterDTO;
import com.company.dto.dtoResponce.TransactionResponceDTO;
import com.company.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @ApiOperation(value = "create", notes = "Method for create Transaction", nickname = "Dev")
    @PreAuthorize("hasRole('ROLE_BANK')")
    @PostMapping("")
    public ResponseEntity<Boolean> create(@RequestBody TransactionDTO dto,
                                          Principal principal) {
        log.info("Transaction: {}", dto);
        return ResponseEntity.ok(transactionService.create(dto, principal));
    }

    @ApiOperation(value = "getPagination", notes = "Method for get Pagination by card ", nickname = "Dev")
    @PreAuthorize("hasAnyRole('ROLE_BANK', 'ROLE_CLIENT')")
    @GetMapping("/by-card{id}")
    public ResponseEntity<Page<TransactionResponceDTO>> getPagintionByFromCard(@PathVariable("id") String id,
                                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(transactionService.getPagintionByCard(id, page, size));
    }

    @ApiOperation(value = "getPagination", notes = "Method for get List by ClientId ")
    @PreAuthorize("hasAnyRole('ROLE_BANK', 'ROLE_CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getListByClientId(@PathVariable("id") String id) {
        return ResponseEntity.ok(transactionService.getListByClientId(id));
    }

    @ApiOperation(value = "getPagination", notes = "Method for get Pagination by phone ")
    @PreAuthorize("hasAnyRole('ROLE_BANK', 'ROLE_CLIENT')")
    @GetMapping("/by-phone/{phone}")
    public ResponseEntity<?> getPagintionByPhone(@PathVariable("phone") String phone,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "5") int size) {
        return null;
    }

    @ApiOperation(value = "filter", notes = "Method for get Transactions by filter (phone,clientId,cardId ", nickname = "Dev")
    @PreAuthorize("hasAnyRole('ROLE_BANK', 'ROLE_CLIENT','ROLE_ADMIN')")
    @GetMapping("/filter/")
    public ResponseEntity<?> filterJpQuery(@RequestBody TransactionFilterDTO dto) {
        log.info("Transaction filter: {}", dto);
        return ResponseEntity.ok(transactionService.filterJpQuery(dto));
    }

}
