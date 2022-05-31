package com.company.controller;

import com.company.dto.client.ClientBioDTO;
import com.company.dto.client.ClientDTO;
import com.company.dto.client.ClientPhoneDTO;
import com.company.dto.client.ClientStatusDTO;
import com.company.service.ClientService;
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
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
@Api(tags = "Client")
public class ClientController {

    private final ClientService clientService;


    @ApiOperation(value = "Create", notes = "Method used for create client")
    @PreAuthorize("hasRole('bank')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid ClientDTO dto,
                                    Principal principal) {
        log.info("{}", dto);
        return ResponseEntity.ok(clientService.create(dto, principal.getName()));
    }

    @ApiOperation(value = "Update Bio", notes = "Method used for update bio")
    @PreAuthorize("hasRole('bank')")
    @PutMapping("/bio/{clientId}")
    public ResponseEntity<?> updateBio(@RequestBody @Valid ClientBioDTO dto,
                                       @PathVariable("clientId") String clientId) {
        log.info("/bio/{clientId}");
        return ResponseEntity.ok(clientService.updateBio(dto, clientId));
    }

    @ApiOperation(value = "Update Phone", notes = "Method used for update phone")
    @PreAuthorize("hasRole('bank')")
    @PutMapping("/phone/{clientId}")
    public ResponseEntity<?> updatePhone(@RequestBody @Valid ClientPhoneDTO dto,
                                         @PathVariable("clientId") String clientId) {
        log.info("/phone");
        return ResponseEntity.ok(clientService.updatePhone(dto, clientId));
    }

    @ApiOperation(value = "Update Status", notes = "Method used for update status")
    @PreAuthorize("hasRole('bank')")
    @PutMapping("/status/{clientId}")
    public ResponseEntity<?> updateStatus(@RequestBody @Valid ClientStatusDTO dto,
                                          @PathVariable("clientId") String clientId) {
        log.info("/status/{clientId}");
        return ResponseEntity.ok(clientService.updateStatus(dto, clientId));
    }

    @ApiOperation(value = "List", notes = "Method used for get list")
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/list")
    public ResponseEntity<?> paginationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size) {
        log.info("/list");
        return ResponseEntity.ok(clientService.paginationList(page, size));
    }

    @ApiOperation(value = "List By Profile", notes = "Method used for list by profile")
    @PreAuthorize("hasAnyRole('admin','profile')")
    @GetMapping("/list/{profileName}")
    public ResponseEntity<?> paginationListByProfileName(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "5") int size,
                                                         @PathVariable("profileName") String profileName,
                                                         Principal principal) {
        log.info("/adm/list/{profileName}");
        if (principal.getName().equals("profile")) {
            return ResponseEntity.ok(clientService.paginationListByProfileName(page, size, principal.getName()));
        }
        return ResponseEntity.ok(clientService.paginationListByProfileName(page, size, profileName));
    }

    @ApiOperation(value = "Get", notes = "Method used for get client")
    @PreAuthorize("hasAnyRole('admin','profile')")
    @GetMapping("/{clientId}")
    public ResponseEntity<?> get(@PathVariable("clientId") String clientId,
                                 Principal principal) {
        log.info("/{clientId}");
        return ResponseEntity.ok(clientService.get(clientId, principal.getName()));
    }

}
