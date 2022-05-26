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
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestParam @Valid ClientDTO dto) {
        log.info("/");
        return ResponseEntity.ok(clientService.create(dto));
    }

    @ApiOperation(value = "Update Bio", notes = "Method used for update bio")
    @PutMapping("/bio")
    public ResponseEntity<?> updateBio(@RequestParam @Valid ClientBioDTO dto,
                                       Principal principal) {
        log.info("/bio");
        return ResponseEntity.ok(clientService.updateBio(dto, principal.getName()));
    }

    @ApiOperation(value = "Update Phone", notes = "Method used for update phone")
    @PutMapping("/phone")
    public ResponseEntity<?> updatePhone(@RequestParam @Valid ClientPhoneDTO dto,
                                         Principal principal) {
        log.info("/phone");
        return ResponseEntity.ok(clientService.updatePhone(dto, principal.getName()));
    }

    @ApiOperation(value = "Update Status", notes = "Method used for update status")
    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(Principal principal) {
        log.info("/status");
        return ResponseEntity.ok(clientService.updateStatus(principal.getName()));
    }

    @ApiOperation(value = "List", notes = "Method used for get list")
    @GetMapping("/list")
    public ResponseEntity<?> paginationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            Principal principal) {
        log.info("/list");
        return ResponseEntity.ok(clientService.paginationList(page, size));
    }

    @ApiOperation(value = "List", notes = "Method used for list by profile")
    @GetMapping("/list/{profileName}")
    public ResponseEntity<?> paginationListByProfileName(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            @PathVariable("profileName") String profileName,
                                            Principal principal) {
        log.info("/list");
        return ResponseEntity.ok(clientService.paginationListByProfileName(page, size, profileName));
    }

    @ApiOperation(value = "Get", notes = "Method used for get client")
    @GetMapping("/{clientId}")
    public ResponseEntity<?> get(@PathVariable("clientId") String clientId,
                                            Principal principal) {
        log.info("/{clientId}");
        return ResponseEntity.ok(clientService.get(clientId));
    }

}
