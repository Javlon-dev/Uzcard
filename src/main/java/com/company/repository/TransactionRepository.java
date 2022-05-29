package com.company.repository;

import com.company.entity.TransactionEntity;
import com.company.mapper.TransactionsInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    @Query("select t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
            "cf.id as cf_id, cf.cardNumber as cf_number," +
            "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
            "ct.id as ct_id, ct.cardNumber as ct_number," +
            "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone " +
            "from TransactionEntity t " +
            "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
            "inner join CardEntity ct on t.toCard = ct.cardNumber " +
            "inner join cf.client clf " +
            "inner join ct.client clt " +
            "where cf.cardNumber = :cardNumber " +
            "or ct.cardNumber = :cardNumber " +
            "order by t.createdDate desc ")
    Page<TransactionsInfoMapper> findAllByCardNumber(Pageable pageable, @Param("cardNumber") String cardNumber);

    @Query("select t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
            "cf.id as cf_id, cf.cardNumber as cf_number," +
            "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
            "ct.id as ct_id, ct.cardNumber as ct_number," +
            "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone " +
            "from TransactionEntity t " +
            "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
            "inner join CardEntity ct on t.toCard = ct.cardNumber " +
            "inner join cf.client clf " +
            "inner join ct.client clt " +
            "where clf.id = :clientId " +
            "or clt.id = :clientId " +
            "order by t.createdDate desc ")
    Page<TransactionsInfoMapper> findAllByClientId(Pageable pageable, @Param("clientId") String clientId);

    @Query("select t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
            "cf.id as cf_id, cf.cardNumber as cf_number," +
            "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
            "ct.id as ct_id, ct.cardNumber as ct_number," +
            "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone " +
            "from TransactionEntity t " +
            "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
            "inner join CardEntity ct on t.toCard = ct.cardNumber " +
            "inner join cf.client clf " +
            "inner join ct.client clt " +
            "where clf.phone = :phone " +
            "or clt.phone = :phone " +
            "order by t.createdDate desc ")
    Page<TransactionsInfoMapper> findAllByPhone(Pageable pageable, @Param("phone") String phone);

    @Query("select t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
            "cf.id as cf_id, cf.cardNumber as cf_number," +
            "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
            "ct.id as ct_id, ct.cardNumber as ct_number," +
            "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone " +
            "from TransactionEntity t " +
            "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
            "inner join CardEntity ct on t.toCard = ct.cardNumber " +
            "inner join cf.client clf " +
            "inner join ct.client clt " +
            "where clf.profileName = :profileName " +
            "or clt.profileName = :profileName " +
            "order by t.createdDate desc ")
    Page<TransactionsInfoMapper> findAllByProfileNameClient(Pageable pageable, @Param("profileName") String profileName);

    @Query("select t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
            "cf.id as cf_id, cf.cardNumber as cf_number," +
            "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
            "ct.id as ct_id, ct.cardNumber as ct_number," +
            "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone " +
            "from TransactionEntity t " +
            "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
            "inner join CardEntity ct on t.toCard = ct.cardNumber " +
            "inner join cf.client clf " +
            "inner join ct.client clt " +
            "where t.profileName = :profileName " +
            "order by t.createdDate desc ")
    Page<TransactionsInfoMapper> findAllByProfileNameTransaction(Pageable pageable, @Param("profileName") String profileName);

    @Query("select t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
            "cf.id as cf_id, cf.cardNumber as cf_number," +
            "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
            "ct.id as ct_id, ct.cardNumber as ct_number," +
            "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone " +
            "from TransactionEntity t " +
            "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
            "inner join CardEntity ct on t.toCard = ct.cardNumber " +
            "inner join cf.client clf " +
            "inner join ct.client clt " +
            "where t.id = :id ")
    Optional<TransactionsInfoMapper> findByIdMapper(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update card set balance = balance - (:amount + :percent) where card_number = :fromCardNumber ; " +
            "update card set balance = balance + :amount where card_number = :toCardNumber ; " +
            "update card set balance = balance + :percent where card_number = :bankCard ; ",
            nativeQuery = true)
    void updateAmount(@Param("fromCardNumber") String fromCardNumber,
                      @Param("toCardNumber") String toCardNumber,
                      @Param("amount") Long amount,
                      @Param("bankCard") String bankCard,
                      @Param("percent") Long percent);
}