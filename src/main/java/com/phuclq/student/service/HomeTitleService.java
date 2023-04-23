package com.phuclq.student.service;

import com.phuclq.student.domain.HomeTitle;

import java.util.List;

public interface HomeTitleService {

    List<HomeTitle> getHomeTitlesAutocomplete(String name);


}
