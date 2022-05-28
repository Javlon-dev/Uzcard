package com.company.service;

import com.company.dto.CardDTO;
import com.company.dto.ClientDTO;
import com.company.dto.TransactionDTO;
import com.company.entity.CardEntity;
import com.company.entity.TransactionEntity;
import com.company.enums.TransactionsStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.mapper.TransactionsInfoMapper;
import com.company.repository.TransactionRepository;
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
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardService cardService;


    public TransactionDTO create(TransactionDTO dto, String profileName) {
        CardEntity fromCard = cardService.getByCardNumber(dto.getFromCardNumber());
        CardEntity toCard = cardService.getByCardNumber(dto.getToCardNumber());

        if (Optional.ofNullable(fromCard).isEmpty()
                || Optional.ofNullable(toCard).isEmpty()) {
            log.warn("Card Not found");
            throw new ItemNotFoundException("Card Not found!");
        }

        if(fromCard.getCardNumber().equals(toCard.getCardNumber())){
            log.warn("Equals Card Numbers fromCard={} toCard={}", fromCard.getCardNumber(), toCard.getCardNumber());
            throw new AppBadRequestException("Equals Card Numbers!");
        }

        if (fromCard.getBalance() < dto.getAmount()) {
            log.warn("Not enough money {}", fromCard.getCardNumber());
            throw new AppBadRequestException("Not enough money!");
        }


        TransactionEntity entity = new TransactionEntity();
        entity.setFromCard(fromCard.getCardNumber());
        entity.setToCard(toCard.getCardNumber());
        entity.setAmount(dto.getAmount());
        entity.setProfileName(profileName);
        entity.setStatus(TransactionsStatus.SUCCESS);

        transactionRepository.save(entity);

        return toDTOMapper(getByIdMapper(entity.getId()));
    }

    public PageImpl<TransactionDTO> paginationListByCardId(int page, int size, String cardId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionRepository.findAllByCardId(pageable, cardId);

        List<TransactionDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> paginationListByClientId(int page, int size, String clientId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionRepository.findAllByClientId(pageable, clientId);

        List<TransactionDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> paginationListByPhone(int page, int size, String phone) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionRepository.findAllByPhone(pageable, phone);

        List<TransactionDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> paginationListByProfileName(int page, int size, String profileName) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TransactionsInfoMapper> entityPage = transactionRepository.findAllByProfileName(pageable, profileName);

        List<TransactionDTO> dtoList = new ArrayList<>();

        entityPage.forEach(mapper -> {
            dtoList.add(toDTOMapper(mapper));
        });

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public TransactionsInfoMapper getByIdMapper(String id) {
        return transactionRepository
                .findByIdMapper(id)
                .orElseThrow(() -> {
                    log.warn("Transaction Not found {}", id);
                    throw new ItemNotFoundException("Transaction Not found!");
                });
    }

    public TransactionDTO toDTOMapper(TransactionsInfoMapper mapper) {
        TransactionDTO dto = new TransactionDTO();

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
