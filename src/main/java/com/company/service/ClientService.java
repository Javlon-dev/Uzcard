package com.company.service;

import com.company.dto.ClientBioDTO;
import com.company.dto.ClientDTO;
import com.company.dto.ClientPhoneDTO;
import com.company.entity.ClientEntity;
import com.company.enums.EntityStatus;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public ClientDTO create(ClientDTO dto) {
        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setStatus(EntityStatus.ACTIVE);
        entity.setProfileName(dto.getProfileName());

        return toDTO(entity);
    }

    public ClientDTO updateBio(ClientBioDTO dto, String clientId) {
        ClientEntity entity = getById(clientId);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUpdatedDate(LocalDateTime.now());

        return toDTO(entity);
    }

    public ClientDTO updatePhone(ClientPhoneDTO dto, String clientId) {
        ClientEntity entity = getById(clientId);
        entity.setPhone(dto.getPhone());
        entity.setUpdatedDate(LocalDateTime.now());

        return toDTO(entity);
    }

    public ClientDTO updateStatus(String clientId) {
        ClientEntity entity = getById(clientId);

        switch (entity.getStatus()) {
            case ACTIVE -> {
                entity.setStatus(EntityStatus.NONACTIVE);
            }
            case NONACTIVE -> {
                entity.setStatus(EntityStatus.BLOCK);
            }
            case BLOCK -> {
                entity.setStatus(EntityStatus.ACTIVE);
            }
        }
        clientRepository.save(entity);
        return toDTO(entity);
    }

    public PageImpl<ClientDTO> paginationList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ClientEntity> entityPage = clientRepository.findAll(pageable);

        List<ClientDTO> dtoList = new ArrayList<>();

        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public ClientDTO get(String clientId) {
        ClientEntity entity = getById(clientId);
// TODO: 25-May-22
        return toDTO(entity);
    }

    public PageImpl<ClientDTO> paginationListByProfileName(int page, int size, String profileName) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ClientEntity> entityPage = clientRepository.findAll(pageable);

        List<ClientDTO> dtoList = new ArrayList<>();

        entityPage.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public ClientEntity getById(String clientId) {
        return clientRepository
                .findById(clientId)
                .orElseThrow(() -> {
                    log.warn("Not found {}", clientId);
                    return new ItemNotFoundException("Not found!");
                });
    }

    public ClientDTO toDTO(ClientEntity entity) {
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(dto.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setProfileName(entity.getProfileName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}
