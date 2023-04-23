package com.phuclq.student.dao;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.domain.Image;
import com.phuclq.student.domain.SellDetail;
import com.phuclq.student.service.HomeTitleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class SellDetailDao  {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SellDetail> getHomeTitlesAutocomplete(SellDetail dto){
        StringBuilder sqlStatement = new StringBuilder();

        sqlStatement.append("SELECT id  ,name as name,create_date as createDate, create_id as createId, ");
        sqlStatement.append("update_date as updateDate , update_id as updateId from image ");
        sqlStatement.append("where 1 =1  ");
        if (dto.getId() != 0) sqlStatement.append("and id =:id");
        if (dto.getName() != null) sqlStatement.append("and name =:name");
        sqlStatement.append(" order by name limit 10   ");
        Query query = entityManager.createNativeQuery(sqlStatement.toString());

        if (dto.getId() != 0)
            query.setParameter("id", dto.getId());
        if (dto.getName() != null)
            query.setParameter("name", dto.getName());
        List<SellDetail> sellDetails = query.getResultList();
        return sellDetails;
    }
}

