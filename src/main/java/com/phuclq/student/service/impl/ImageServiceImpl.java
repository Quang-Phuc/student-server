package com.phuclq.student.service.impl;

import com.phuclq.student.domain.HomeTitle;
import com.phuclq.student.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public List<HomeTitle> getImageAutocomplete(String name) {
        return null;
    }
}

