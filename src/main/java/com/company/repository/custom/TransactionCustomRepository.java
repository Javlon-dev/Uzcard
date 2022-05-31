package com.company.repository.custom;

import com.company.dto.transaction.TransactionFilterDTO;
import com.company.enums.TransactionsStatus;
import com.company.mapper.TransactionInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionCustomRepository {

    private final EntityManager entityManager;


    public List<TransactionInfoMapper> filter(TransactionFilterDTO dto) {
        StringBuilder sql = new StringBuilder(
                "select new com.company.mapper.TransactionInfoMapper( t.id as t_id, t.amount as t_amount, t.createdDate as t_created_date, t.status as t_status," +
                        "cf.id as cf_id, cf.cardNumber as cf_number," +
                        "clf.id as clf_id, clf.name as clf_name, clf.surname as clf_surname, clf.phone as clf_phone," +
                        "ct.id as ct_id, ct.cardNumber as ct_number," +
                        "clt.id as clt_id, clt.name as clt_name, clt.surname as clt_surname, clt.phone as clt_phone) " +
                        "from TransactionEntity t " +
                        "inner join CardEntity cf on t.fromCard = cf.cardNumber " +
                        "inner join CardEntity ct on t.toCard = ct.cardNumber " +
                        "inner join cf.client clf " +
                        "inner join ct.client clt ");

        Map<String, Object> params = new HashMap<>();

        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            sql.append(" where t.status = :status ");
            params.put("status", dto.getStatus());
        }else {
            sql.append(" where t.status = :status ");
            params.put("status", TransactionsStatus.SUCCESS);
        }

        if (Optional.ofNullable(dto.getClientId()).isPresent()) {
            sql.append(" and clt.id = :id or clt.id = :id");
            params.put("id", dto.getClientId());
        }

        if (Optional.ofNullable(dto.getCardNumber()).isPresent()) {
            sql.append(" and t.fromCard = :cardNumber or t.toCard = :cardNumber");
            params.put("cardNumber", dto.getCardNumber());
        }

        if (Optional.ofNullable(dto.getFromAmount()).isPresent() &&
                Optional.ofNullable(dto.getToAmount()).isPresent()) {
            sql.append(" and t.amount between :fromAmount and :toAmount ");
            params.put("fromAmount", Long.parseLong(dto.getFromAmount() + "00"));
            params.put("toAmount",  Long.parseLong(dto.getToAmount() + "00"));
        } else if (Optional.ofNullable(dto.getFromAmount()).isPresent()) {
            sql.append(" and t.amount > :fromAmount ");
            params.put("fromAmount", Long.parseLong(dto.getFromAmount() + "00"));
        } else if (Optional.ofNullable(dto.getToAmount()).isPresent()) {
            sql.append(" and t.amount < :toAmount ");
            params.put("toAmount",  Long.parseLong(dto.getToAmount() + "00"));
        }

        if (Optional.ofNullable(dto.getFromDate()).isPresent() &&
                Optional.ofNullable(dto.getToDate()).isPresent()) {
            sql.append(" and date(t.createdDate) between :fromDate and :toDate ");
            params.put("fromDate", dto.getFromDate());
            params.put("toDate", dto.getToDate());
        } else if (Optional.ofNullable(dto.getFromDate()).isPresent()) {
            sql.append(" and date(t.createdDate) > :fromDate ");
            params.put("fromDate", dto.getFromDate());
        } else if (Optional.ofNullable(dto.getToDate()).isPresent()) {
            sql.append(" and date(t.createdDate) < :toDate ");
            params.put("toDate", dto.getToDate());
        }

        if (Optional.ofNullable(dto.getProfileName()).isPresent()) {
            sql.append(" and t.profileName = :profileName ");
            params.put("profileName", dto.getProfileName());
        }

        Query query = entityManager.createQuery(sql.toString(), TransactionInfoMapper.class);

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
