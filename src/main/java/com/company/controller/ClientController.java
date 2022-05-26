package com.company.controller;

import com.company.dto.ClientBioDTO;
import com.company.dto.ClientDTO;
import com.company.dto.ClientPhoneDTO;
import com.company.service.ClientService;
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
@RequestMapping("/client")
@RequiredArgsConstructor
@Api(tags = "Client")
public class ClientController {

    private final ClientService clientService;


    @ApiOperation(value = "Create", notes = "Method used for create client")
    @PostMapping("/bank/")
    public ResponseEntity<?> create(@RequestParam @Valid ClientDTO dto) {
        log.info("/bank/");
        return ResponseEntity.ok(clientService.create(dto));
    }

    @ApiOperation(value = "Update Bio", notes = "Method used for update bio")
    @PutMapping("/bank/bio")
    public ResponseEntity<?> updateBio(@RequestParam @Valid ClientBioDTO dto,
                                       Principal principal) {
        log.info("/bank/bio");
        return ResponseEntity.ok(clientService.updateBio(dto, principal.getName()));
    }

    @ApiOperation(value = "Update Phone", notes = "Method used for update phone")
    @PutMapping("/bank/phone")
    public ResponseEntity<?> updatePhone(@RequestParam @Valid ClientPhoneDTO dto,
                                         Principal principal) {
        log.info("/bank/phone");
        return ResponseEntity.ok(clientService.updatePhone(dto, principal.getName()));
    }

    @ApiOperation(value = "Update Status", notes = "Method used for update status")
    @PutMapping("/bank/status")
    public ResponseEntity<?> updateStatus(Principal principal) {
        log.info("/bank/status");
        return ResponseEntity.ok(clientService.updateStatus(principal.getName()));
    }

    @ApiOperation(value = "List", notes = "Method used for get list")
    @GetMapping("/adm/list")
    public ResponseEntity<?> paginationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            Principal principal) {
        log.info("/adm/list");
        return ResponseEntity.ok(clientService.paginationList(page, size));
    }

    @ApiOperation(value = "List", notes = "Method used for list by profile")
    @GetMapping("/adm/list/{profileName}")
    public ResponseEntity<?> paginationListByProfileName(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            @PathVariable("profileName") String profileName,
                                            Principal principal) {
        log.info("/adm/list/{profileName}");
        return ResponseEntity.ok(clientService.paginationListByProfileName(page, size, profileName));
    }

    @ApiOperation(value = "Get", notes = "Method used for get client")
    @GetMapping("/profile/{clientId}")
    public ResponseEntity<?> get(@PathVariable("clientId") String clientId,
                                            Principal principal) {
        log.info("/profile/{clientId}");
        return ResponseEntity.ok(clientService.get(clientId));
    }

}
