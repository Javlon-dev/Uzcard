package com.company.controller;

import com.company.dto.*;
import com.company.service.CardService;
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
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
@Api(tags = "Card")
public class CardController {

    private final CardService cardService;


    @ApiOperation(value = "Create", notes = "Method used for create card")
    @PreAuthorize("hasRole('bank')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid CardDTO dto) {
        log.info("");
        return ResponseEntity.ok(cardService.create(dto));
    }

    @ApiOperation(value = "Update Status", notes = "Method used for update status")
    @PreAuthorize("hasAnyRole('bank', 'profile')")
    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody @Valid CardStatusDTO dto,
                                          Principal principal) {
        log.info("/status");
        return ResponseEntity.ok(cardService.updateStatus(dto, principal.getName()));
    }

    @ApiOperation(value = "Assign Phone", notes = "Method used for assign phone")
    @PreAuthorize("hasRole('bank')")
    @PutMapping("/phone/{clientId}")
    public ResponseEntity<?> assignPhone(@RequestBody @Valid CardNumberDTO dto,
                                         @PathVariable("clientId") String clientId) {
        log.info("/phone/{clientId}");
        return ResponseEntity.ok(cardService.assignPhone(dto, clientId));
    }

    @ApiOperation(value = "List By Phone", notes = "Method used for get list by phone")
    @PreAuthorize("hasAnyRole('bank','profile','admin')")
    @GetMapping("/list/{phone}")
    public ResponseEntity<?> getCardListByPhone(@PathVariable("phone") String phone) {
        log.info("/list/{phone}");
        return ResponseEntity.ok(cardService.getCardListByPhone(phone));
    }

    @ApiOperation(value = "Balance", notes = "Method used for get balance from card")
    @PreAuthorize("hasAnyRole('bank','profile','admin')")
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestBody @Valid CardNumberDTO dto) {
        log.info("/balance");
        return ResponseEntity.ok(cardService.getBalance(dto));
    }

    @ApiOperation(value = "List By Client", notes = "Method used for get list by client")
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getCardListByClientId(@PathVariable("clientId") String clientId) {
        log.info("/client/{clientId}");
        return ResponseEntity.ok(cardService.getCardListByClientId(clientId));
    }

    @ApiOperation(value = "Get", notes = "Method used for get card")
    @PreAuthorize("hasAnyRole('bank','profile','admin')")
    @GetMapping("")
    public ResponseEntity<?> get(@RequestBody CardNumberDTO dto,
                                 Principal principal) {
        log.info("");
        return ResponseEntity.ok(cardService.get(dto));
    }
}
