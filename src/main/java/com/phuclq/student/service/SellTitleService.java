package com.phuclq.student.service;

import com.phuclq.student.domain.HomeTitle;

import java.util.List;

public interface SellTitleService {

    List<HomeTitle> getSellTitleAutocomplete(String name);


}
