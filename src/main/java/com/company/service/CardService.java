package com.company.service;

import com.company.dto.card.CardDTO;
import com.company.dto.card.CardFilterDTO;
import com.company.dto.card.CardNumberDTO;
import com.company.dto.card.CardStatusDTO;
import com.company.dto.client.ClientDTO;
import com.company.entity.CardEntity;
import com.company.enums.EntityStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemAlreadyExistsException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.custom.CardCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardCustomRepository cardCustomRepository;

    @Value("${message.card.type}")
    private String cardType;

    @Value("${message.expiration.year}")
    private String expirationYear;


    public CardDTO create() {
        String cardNumber = cardType + new Random().nextLong(1000_0000_0000L, 9999_9999_9999L);
        if (Optional.ofNullable(getByCardNumber(cardNumber)).isPresent()) {
            log.warn("Card number already exists {}", cardNumber);
            throw new ItemAlreadyExistsException("This card number already exists!");
        }

        CardEntity entity = new CardEntity();
        entity.setCardNumber(cardNumber);
        entity.setStatus(EntityStatus.INACTIVE);

        cardRepository.save(entity);
        return toDTO(entity);
    }

    public CardDTO updateStatus(CardStatusDTO dto, String profileName) {
        CardEntity entity = getByCardNumber(dto.getCardNumber());

        if (entity.getStatus().equals(dto.getStatus())) {
            return toDTO(entity);
        }

        if (profileName.equals("profile")) {
            switch (entity.getStatus()) {
                case ACTIVE -> entity.setStatus(EntityStatus.BLOCK);
                case BLOCK -> entity.setStatus(EntityStatus.ACTIVE);
            }
            cardRepository.save(entity);
            return toDTO(entity);
        }

        entity.setStatus(dto.getStatus());
        cardRepository.save(entity);
        return toDTO(entity);
    }

    public CardDTO assignPhone(CardNumberDTO dto, String clientId) {
        CardEntity entity = getByCardNumber(dto.getCardNumber());

        entity.setExpiredDate(LocalDate.now().plusYears(Long.parseLong(expirationYear)));
        entity.setStatus(EntityStatus.ACTIVE);
        entity.setClientId(clientId);

        cardRepository.save(entity);
        return toDTO(entity);
    }

    public List<CardDTO> getCardListByPhone(String phone) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        return cardRepository
                .findAllByPhoneNumber(sort, phone)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CardDTO> getCardListByClientId(String clientId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        return cardRepository
                .findAllByClientId(sort, clientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CardDTO get(CardNumberDTO dto) {
        CardEntity entity = getByCardNumber(dto.getCardNumber());
        if (Optional.ofNullable(getByCardNumber(dto.getCardNumber())).isPresent()) {
            return toDTO(entity);
        }
        log.warn("Card Not found {}", dto.getCardNumber());
        throw new ItemNotFoundException("Card Not found!");
    }

    public String getBalance(String cardNumber) {
        CardEntity entity = getByCardNumber(cardNumber);
        if (Optional.ofNullable(getByCardNumber(cardNumber)).isPresent()) {
            return balanceToSum(entity.getBalance());
        }
        log.warn("Card Not found {}", cardNumber);
        throw new ItemNotFoundException("Card Not found!");
    }

    public String balanceToSum(Long balance) {
        String cash = balance.toString();
        if (cash.equals("0")) {
            return "0 sum";
        }
        if (cash.length() <= 2) {
            return "0,0" + balance + " sum";
        }
        if (cash.length() <= 3) {
            return "0," + balance + " sum";
        }
        return cash.substring(0, cash.length() - 2) + "," + cash.substring(cash.length() - 2) + " sum";
    }

    public List<CardDTO> filter(CardFilterDTO dto) {
        return cardCustomRepository
                .filter(dto)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CardEntity getByCardNumber(String cardNumber) {
        return cardRepository
                .findByCardNumberAndVisible(cardNumber, true)
                .orElse(null);
    }

    public CardEntity getByCardNumberActive(String cardNumber) {
        return cardRepository
                .findByCardNumberAndVisibleAndStatus(cardNumber, true, EntityStatus.ACTIVE)
                .orElseThrow(() -> {
                    log.warn("Inactive Card {}", cardNumber);
                    throw new AppBadRequestException("Inactive Card!");
                });
    }

    public CardDTO toDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setId(entity.getId());
        dto.setCardNumber(entity.getCardNumber());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setStatus(entity.getStatus());
        dto.setCash(balanceToSum(entity.getBalance()));

        if (Optional.ofNullable(entity.getClient()).isPresent()) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName(entity.getClient().getName());
            clientDTO.setSurname(entity.getClient().getSurname());
            dto.setClient(clientDTO);
        }

        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
