package com.phuclq.student.service.impl;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.service.HomeTitleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeTitleServiceImpl implements HomeTitleService {
    @Override
    public List<HomeTitle> getHomeTitlesAutocomplete(String name) {
        return null;
    }
}

