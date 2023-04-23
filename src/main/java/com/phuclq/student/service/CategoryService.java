package com.phuclq.student.service;

import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.File;
import com.phuclq.student.dto.CategoryFileDTO;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.dto.CategoryHomeResult;
import com.phuclq.student.dto.FileByCategoryDto;
import com.phuclq.student.dto.FileHomeDoFilterDTO;
import com.phuclq.student.dto.FileResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findAllById(int id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(int id);

    List<CategoryHomeDTO> getCategoriesHome();

    Page<CategoryFileDTO>  findFileFromCategories(Pageable pageable) ;
    
    FileByCategoryDto fileFromCategorie(int id, Pageable pageable) ;

}
