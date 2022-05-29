package com.company.repository;

import com.company.entity.CardEntity;
import com.company.enums.EntityStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {

    Optional<CardEntity> findByCardNumberAndVisible(String cardNumber, Boolean visible);

    Optional<CardEntity> findByCardNumberAndVisibleAndStatus(String cardNumber, Boolean visible, EntityStatus status);


    @Query("select cd from CardEntity cd " +
            "inner join cd.client as cl " +
            "where cl.phone = :phone ")
    List<CardEntity> findAllByPhoneNumber(Sort sort, @Param("phone") String phone);

    List<CardEntity> findAllByClientId(Sort sort, String clientId);
}