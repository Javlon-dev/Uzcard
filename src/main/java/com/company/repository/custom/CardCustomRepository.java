package com.company.repository.custom;

import com.company.dto.CardDTO;
import com.company.dto.CardFilterDTO;
import com.company.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardCustomRepository {

    private final EntityManager entityManager;

    public List<CardDTO> filter(CardFilterDTO dto) {
        String sql = "select c from CardEntity c where c.visible = true";
        Query query = entityManager.createQuery(sql, CardEntity.class);
        List<CardEntity> resultList = query.getResultList();


        return null;
    }
}
