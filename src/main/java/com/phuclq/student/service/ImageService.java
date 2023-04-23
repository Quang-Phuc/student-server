package com.phuclq.student.service;

import com.phuclq.student.domain.HomeTitle;

import java.util.List;

public interface ImageService {

    List<HomeTitle> getImageAutocomplete(String name);


}
