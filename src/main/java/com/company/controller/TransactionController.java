package com.company.controller;

import com.company.dto.ClientPhoneDTO;
import com.company.dto.TransactionDTO;
import com.company.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Api(tags = "Transaction")
public class TransactionController {

    private final TransactionService transactionService;


    @ApiOperation(value = "Create", notes = "Method used for create transaction")
    @PreAuthorize("hasRole('bank')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TransactionDTO dto,
                                    Principal principal) {
        log.info("{}", dto);
        return ResponseEntity.ok(transactionService.create(dto, principal.getName()));
    }

    @ApiOperation(value = "List By Card", notes = "Method used for get pagination list by card id")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/card/{cardId}")
    public ResponseEntity<?> paginationListByCardId(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "5") int size,
                                                    @PathVariable("cardId") String cardId) {
        log.info("/card/{cardId} {}", cardId);
        return ResponseEntity.ok(transactionService.paginationListByCardId(page, size, cardId));
    }

    @ApiOperation(value = "List By Client", notes = "Method used for get pagination list by client id")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> paginationListByClientId(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "5") int size,
                                                      @PathVariable("clientId") String clientId) {
        log.info("/client/{clientId} {}", clientId);
        return ResponseEntity.ok(transactionService.paginationListByClientId(page, size, clientId));
    }

    @ApiOperation(value = "List By Phone", notes = "Method used for get pagination list by phone number")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/phone")
    public ResponseEntity<?> paginationListByPhone(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "5") int size,
                                                   @RequestBody @Valid ClientPhoneDTO dto) {
        log.info("/phone {}", dto);
        return ResponseEntity.ok(transactionService.paginationListByPhone(page, size, dto.getPhone()));
    }

    @ApiOperation(value = "List By Profile Name", notes = "Method used for get pagination list by profile name")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/profile/{profileName}")
    public ResponseEntity<?> paginationListByProfileName(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "5") int size,
                                                         @PathVariable("profileName") String profileName) {
        log.info("/profile/{profileName} {}", profileName);
        return ResponseEntity.ok(transactionService.paginationListByProfileName(page, size, profileName));
    }

}
