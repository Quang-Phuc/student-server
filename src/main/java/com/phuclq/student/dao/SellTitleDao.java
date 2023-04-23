package com.phuclq.student.dao;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.service.HomeTitleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SellTitleDao implements HomeTitleService {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<HomeTitle> getHomeTitlesAutocomplete(String name) {
        return null;
    }
}

