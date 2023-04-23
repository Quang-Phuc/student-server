package com.phuclq.student.service.impl;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.service.SellTitleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellTitleServiceImpl implements SellTitleService {
    @Override
    public List<HomeTitle> getSellTitleAutocomplete(String name) {
        return null;
    }
}

