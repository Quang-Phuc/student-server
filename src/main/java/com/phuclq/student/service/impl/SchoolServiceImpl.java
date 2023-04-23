package com.phuclq.student.service.impl;

import com.phuclq.student.domain.School;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.repository.SchoolRepository;
import com.phuclq.student.service.SchoolService;
import com.phuclq.student.utils.CommonFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<School> findAll() {
//        StringBuilder sqlStatement = new StringBuilder();
//        int id = 2;
//        sqlStatement.append("SELECT id  ,school_name as schoolName from school where 1 = 1");
//        if(id!=0)sqlStatement.append("id =:id");
//        Query query = entityManager.createNativeQuery(sqlStatement.toString());
//
//        if(id!=0)
//        query.setParameter("id", id);
//        List<School> school = query.getResultList();
//        return school;
        return schoolRepository.findAll();
    }

    @Override
    public School findAllById(int id) {
        return schoolRepository.findSchoolById(id);
    }

    @Override
    public School save(School school) {
        return schoolRepository.save(school);
    }

    @Override
    public void deleteById(int id) {
        schoolRepository.deleteById(id);
    }

    @Override
    public List<School> saveSchools() {
        List<School> schools = CommonFunction.readSchoolsFromExcel();
        List<School> schoolOlds = schoolRepository.findAll();
        for (School school : schools) {
            if (schoolOlds.contains(school)) {
                return new ArrayList<>();
            }
        }
        return schoolRepository.saveAll(schools);
    }

    @Override
    public School update(School school) {
        School schoolById = schoolRepository.findSchoolById(school.getId());
        schoolById.setSchoolName(school.getSchoolName());
        return schoolRepository.save(schoolById);
    }
}
