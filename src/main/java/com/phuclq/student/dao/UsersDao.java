package com.phuclq.student.dao;

import static com.phuclq.student.common.Constants.SQL_USER_JOIN;

import com.phuclq.student.common.Constants;
import com.phuclq.student.dto.FileHomePageRequest;
import com.phuclq.student.dto.FileResultDto;
import com.phuclq.student.dto.UserAdminResult;
import com.phuclq.student.dto.UserHistoryDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersDao  {

    @PersistenceContext
    private EntityManager entityManager;



    public Page<UserAdminResult> listUserAdmin(FileHomePageRequest request, Pageable pageable) {
        List<Object> objList = null;

        StringBuilder sqlStatement = new StringBuilder();
        StringBuilder sqlStatementLimit = new StringBuilder();
        List<Object> listParam = new ArrayList<Object>();
            sqlStatement.append(SQL_USER_JOIN);

        sqlStatement.append(" where 1 = 1  ");
        if (request.getDateFrom() != null) {
            sqlStatement.append(" and u.created_date >= ? ");
            listParam.add(request.getDateFrom());
        }
        if (request.getDateTo() != null) {
            sqlStatement.append(" and u.created_date <= ? ");
            listParam.add(request.getDateTo());
        }
        if (request.getSearch() != null && !request.getSearch().isEmpty()) {
            sqlStatement.append(" and (LOWER(u.user_name) like LOWER(?) ");
            sqlStatement.append(" or LOWER( u.email) like LOWER(?) ");
            sqlStatement.append(" or LOWER(u.phone) like LOWER(?) ");
            sqlStatement.append(" or LOWER(u.full_name) like LOWER(?)) ");
            listParam.add("%" + request.getSearch() + "%");
            listParam.add("%" + request.getSearch() + "%");
            listParam.add("%" + request.getSearch() + "%");
            listParam.add("%" + request.getSearch() + "%");
        }
        Query queryCount = entityManager.createNativeQuery(" select count(*) " + sqlStatement);
        for (int i = 0; i < listParam.size(); i++) {
            queryCount.setParameter(i + 1, listParam.get(i));
        }
        Integer count = ((Number) queryCount.getSingleResult()).intValue();

        sqlStatementLimit.append(" LIMIT ? OFFSET ?");
        listParam.add(pageable.getPageSize());
        listParam.add(pageable.getPageSize() * pageable.getPageNumber());
        Query query = entityManager.createNativeQuery(Constants.SQL_USER + sqlStatement+Constants.SQL_USER_GROUP+sqlStatementLimit);
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i + 1, listParam.get(i));
        }
        objList = query.getResultList();
        List<UserAdminResult> list = new ArrayList<>();
        for (Object obj : objList) {
            UserAdminResult result = new UserAdminResult((Object[]) obj);
            list.add(result);
        }

        Page<UserAdminResult> listCategory = new PageImpl<UserAdminResult>(list, pageable, count);
        FileResultDto fileResultDto = new FileResultDto();
        List<UserHistoryDTO> fileHistoryHome = new ArrayList<>();


        return listCategory;

    }
}

