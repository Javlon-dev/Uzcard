package com.company.service;

import com.company.dto.CardDTO;
import com.company.dto.ClientDTO;
import com.company.dto.TransactionsDTO;
import com.company.entity.CardEntity;
import com.company.entity.TransactionsEntity;
import com.company.enums.TransactionsStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.mapper.TransactionsInfoMapper;
import com.company.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final CardService cardService;


    public TransactionsDTO create(TransactionsDTO dto, String profileName) {
        CardEntity fromCard = cardService.getByCardNumber(dto.getFromCardNumber());
        CardEntity toCard = cardService.getByCardNumber(dto.getToCardNumber());

        if (Optional.ofNullable(fromCard).isEmpty()
                || Optional.ofNullable(toCard).isEmpty()) {
            log.warn("Card Not found");
            throw new ItemNotFoundException("Card Not found!");
        }

        if (fromCard.getBalance() < dto.getAmount()) {
            log.warn("Not enough money {}", fromCard.getCardNumber());
            throw new AppBadRequestException("Not enough money!");
        }

        TransactionsEntity entity = new TransactionsEntity();
        entity.setFromCard(fromCard.getCardNumber());
        entity.setToCard(toCard.getCardNumber());
        entity.setAmount(dto.getAmount());
        entity.setProfileName(profileName);
        entity.setStatus(TransactionsStatus.SUCCESS);

        transactionsRepository.save(entity);

        return toDTOMapper(getByIdMapper(entity.getId()));
    }

    public PageImpl<TransactionsDTO> paginationListByCardId(int page, int size, String cardId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionsRepository.findAllByCardId(pageable, cardId);

        List<TransactionsDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionsDTO> paginationListByClientId(int page, int size, String clientId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionsRepository.findAllByClientId(pageable, clientId);

        List<TransactionsDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionsDTO> paginationListByPhone(int page, int size, String phone) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionsRepository.findAllByPhone(pageable, phone);

        List<TransactionsDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionsDTO> paginationListByProfileName(int page, int size, String profileName) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionsRepository.findAllByProfileName(pageable, profileName);

        List<TransactionsDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public TransactionsInfoMapper getByIdMapper(String id) {
        return transactionsRepository
                .findByIdMapper(id)
                .orElseThrow(() -> {
                    log.warn("Transaction Not found {}", id);
                    throw new ItemNotFoundException("Transaction Not found!");
                });
    }

    public TransactionsDTO toDTOMapper(TransactionsInfoMapper mapper) {
        TransactionsDTO dto = new TransactionsDTO();

        dto.setId(mapper.getT_id());
        dto.setCash(cardService.balanceToSum(mapper.getT_amount()));
        dto.setStatus(mapper.getT_status());
        dto.setCreatedDate(mapper.getT_created_date());

        dto.setFromCard(new CardDTO(mapper.getCf_id(), encryptCardNumber(mapper.getCf_number()),
                new ClientDTO(mapper.getClf_id(), mapper.getClf_name(), mapper.getClf_surname(), mapper.getClf_phone())));

        dto.setToCard(new CardDTO(mapper.getCt_id(), encryptCardNumber(mapper.getCt_number()),
                new ClientDTO(mapper.getClt_id(), mapper.getClt_name(), mapper.getClt_surname(), mapper.getClt_phone())));

        return dto;
    }

    public String encryptCardNumber(String cardNumber) {
        StringBuilder builder = new StringBuilder(cardNumber.substring(0, 4));
        builder.append("********").append(cardNumber, builder.length(), cardNumber.length());
        return builder.toString();
    }
}
