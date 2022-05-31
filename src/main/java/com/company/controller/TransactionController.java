package com.company.controller;

import com.company.dto.card.CardNumberDTO;
import com.company.dto.client.ClientPhoneDTO;
import com.company.dto.transaction.TransactionDTO;
import com.company.dto.transaction.TransactionFilterDTO;
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
    public ResponseEntity<?> create(@RequestBody @Valid TransactionDTO dto,
                                    Principal principal) {
        log.info("{}", dto);
        return ResponseEntity.ok(transactionService.create(dto, principal.getName()));
    }

    @ApiOperation(value = "List By Card", notes = "Method used for get pagination list by card id")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/card")
    public ResponseEntity<?> paginationListByCardId(@RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "5") int size,
                                                    @RequestBody @Valid CardNumberDTO dto) {
        log.info("/card {}", dto);
        return ResponseEntity.ok(transactionService.paginationListByCardId(page, size, dto.getCardNumber()));
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

    @ApiOperation(value = "List By Profile Name From Client",
            notes = "Method used for get pagination list by profile name from client")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/profile/{profileName}")
    public ResponseEntity<?> paginationListByProfileNameClient(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "5") int size,
                                                               @PathVariable("profileName") String profileName) {
        log.info("/profile/{profileName} {}", profileName);
        return ResponseEntity.ok(transactionService.paginationListByProfileNameClient(page, size, profileName));
    }

    @ApiOperation(value = "List By Profile Name From Transaction",
            notes = "Method used for get pagination list by profile name from transaction")
    @PreAuthorize("hasRole('bank')")
    @GetMapping("/{profileName}")
    public ResponseEntity<?> paginationListByProfileNameTransaction(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "5") int size,
                                                                    @PathVariable("profileName") String profileName) {
        log.info("/profile/{profileName} {}", profileName);
        return ResponseEntity.ok(transactionService.paginationListByProfileNameTransaction(page, size, profileName));
    }

    @ApiOperation(value = "List By Filter", notes = "Method used for get list by filter")
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody TransactionFilterDTO dto) {
        return ResponseEntity.ok(transactionService.filter(dto));
    }

}
