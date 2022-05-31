package com.company.repository.custom;

import com.company.dto.card.CardFilterDTO;
import com.company.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardCustomRepository {

    private final EntityManager entityManager;

    @Value("${message.bank.key.word}")
    private String keyWord;


    public List<CardEntity> filter(CardFilterDTO dto) {
        StringBuilder sql = new StringBuilder(
                "select c from CardEntity c " +
                        "left join c.client cl " +
                        "where c.visible = true " +
                        "and cast(c.status as string) <> :keyWord ");

        Map<String, Object> params = new HashMap<>();
        params.put("keyWord", keyWord);

        if (Optional.ofNullable(dto.getPhone()).isPresent()) {
            sql.append(" and cl.phone = :phone ");
            params.put("phone", dto.getPhone());
        }

        if (Optional.ofNullable(dto.getCardNumber()).isPresent()) {
            sql.append(" and c.cardNumber = :cardNumber ");
            params.put("cardNumber", dto.getCardNumber());
        }

        if (Optional.ofNullable(dto.getExpDate()).isPresent()) {
            sql.append(" and c.expiredDate = :expDate ");
            params.put("expDate", dto.getExpDate());
        }

        if (Optional.ofNullable(dto.getCreatedDate()).isPresent()) {
            sql.append(" and c.createdDate = :createdDate ");
            params.put("createdDate", dto.getCreatedDate());
        }

        if (Optional.ofNullable(dto.getFromBalance()).isPresent() &&
                Optional.ofNullable(dto.getToBalance()).isPresent()) {
            sql.append(" and c.balance between :fromBalance and :toBalance ");
            params.put("fromBalance", Long.parseLong(dto.getFromBalance() + "00"));
            params.put("toBalance",  Long.parseLong(dto.getToBalance() + "00"));
        } else if (Optional.ofNullable(dto.getFromBalance()).isPresent()) {
            sql.append(" and c.balance > :fromBalance ");
            params.put("fromBalance", Long.parseLong(dto.getFromBalance() + "00"));
        } else if (Optional.ofNullable(dto.getToBalance()).isPresent()) {
            sql.append(" and c.balance < :toBalance ");
            params.put("toBalance", Long.parseLong(dto.getToBalance() + "00"));
        }

        if (Optional.ofNullable(dto.getProfileName()).isPresent()) {
            sql.append(" and cl.profileName = :profileName ");
            params.put("profileName", dto.getProfileName());
        }

        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            sql.append(" and c.status = :status ");
            params.put("status", dto.getStatus());
        }

        if (Optional.ofNullable(dto.getClientId()).isPresent()) {
            sql.append(" and cl.id = :id ");
            params.put("id", dto.getClientId());
        }

        if (Optional.ofNullable(dto.getClientName()).isPresent()) {
            sql.append(" and cl.name = :name ");
            params.put("name", dto.getClientName());
        }

        if (Optional.ofNullable(dto.getClientStatus()).isPresent()) {
            sql.append(" and cl.status = :clStatus ");
            params.put("clStatus", dto.getClientStatus());
        }

        Query query = entityManager.createQuery(sql.toString(), CardEntity.class);

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
