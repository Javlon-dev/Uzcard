package com.company.entity;

import com.company.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "card")
@Getter
@Setter
public class CardEntity extends BaseEntity {

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column
    private Long balance = 0L;

    @JoinColumn(name = "client_id")
    private String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @Column
    private Boolean visible;
}
