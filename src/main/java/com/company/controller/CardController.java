package com.company.controller;

import com.company.dto.CardDTO;
import com.company.dto.CardNumberDTO;
import com.company.dto.ClientBioDTO;
import com.company.dto.ClientPhoneDTO;
import com.company.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
@Api(tags = "Card")
public class CardController {

    private final CardService cardService;


    @ApiOperation(value = "Create", notes = "Method used for create card")
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestParam @Valid CardDTO dto) {
        log.info("/");
        return ResponseEntity.ok(cardService.create(dto));
    }

    @ApiOperation(value = "Update Status", notes = "Method used for update status")
    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(Principal principal) {
        log.info("/status");
        return ResponseEntity.ok(cardService.updateStatus(principal.getName()));
    }

    @ApiOperation(value = "Assign Phone", notes = "Method used for assign phone")
    @PutMapping("/phone")
    public ResponseEntity<?> assignPhone(@RequestParam @Valid CardNumberDTO dto,
                                         Principal principal) {
        log.info("/phone");
        return ResponseEntity.ok(cardService.assignPhone(dto, principal.getName()));
    }

    @ApiOperation(value = "List", notes = "Method used for get list by phone")
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getCardListByPhone(@PathVariable("phone") String phone,
                                                Principal principal) {
        log.info("/phone/{phone}");
        return ResponseEntity.ok(cardService.getCardListByPhone(phone));
    }

    @ApiOperation(value = "List", notes = "Method used for get list by client")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getCardListByClientId(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "5") int size,
                                                   @PathVariable("clientId") String clientId,
                                                   Principal principal) {
        log.info("/client/{clientId}");
        return ResponseEntity.ok(cardService.getCardListByClientId(clientId));
    }

    @ApiOperation(value = "Get", notes = "Method used for get card")
    @GetMapping("/")
    public ResponseEntity<?> get(@RequestBody CardNumberDTO dto,
                                 Principal principal) {
        log.info("/");
        return ResponseEntity.ok(cardService.get(dto));
    }
}
