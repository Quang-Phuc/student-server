package com.phuclq.student.service.impl;

import com.phuclq.student.domain.Category;
import com.phuclq.student.domain.File;
import com.phuclq.student.dto.CategoryFileDTO;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.dto.FileByCategoryDto;
import com.phuclq.student.dto.FileDTO;
import com.phuclq.student.dto.FileHomeDoFilterDTO;
import com.phuclq.student.dto.FileResult;
import com.phuclq.student.dto.FileResultInterface;
import com.phuclq.student.repository.CategoryRepository;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    FileRepository fileRepository;

    
    
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findAllById(int id) {
        return categoryRepository.findAllById(id);
    }

    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryHomeDTO> getCategoriesHome() {
        List<CategoryHomeDTO> categoryHomeDTOList = categoryRepository.getCategoriesHome().stream().map(CategoryHomeDTO::new).collect(Collectors.toList());
        return categoryHomeDTOList;
    }

    @Override
    public Category update(Category category) {

        Category CategoryById = findAllById(category.getId());
        CategoryById.setCategory(category.getCategory());

        return categoryRepository.save(CategoryById);
    }

    @Override
    public Page<CategoryFileDTO> findFileFromCategories(Pageable pageable) {


        List<CategoryFileDTO> listCatelogyDTO = new ArrayList<CategoryFileDTO>();
        List<Category> catelogyList = categoryRepository.findAll();

        catelogyList.forEach(category -> {
            CategoryFileDTO categoryFileDTO = new CategoryFileDTO();

            categoryFileDTO.setId(category.getId());
            categoryFileDTO.setNameCategory(category.getCategory());

            List<File> fileList = fileRepository.findAllByCategoryId(category.getId());
            List<FileDTO> fileDTOS = fileList.stream().map(FileDTO::new).collect(Collectors.toList());
            categoryFileDTO.setFileDTOList(fileDTOS);

            listCatelogyDTO.add(categoryFileDTO);
        });

        Page<CategoryFileDTO> pagesCatelogy = new PageImpl<CategoryFileDTO>(listCatelogyDTO, pageable, listCatelogyDTO.size());
        return pagesCatelogy;
    }

	@Override
	public FileByCategoryDto fileFromCategorie(int id, Pageable pageable) {
		Category category = categoryRepository.findById(id).get();
		FileByCategoryDto files = new FileByCategoryDto();
		Page<FileResultInterface> page = fileRepository.findByCategoryId(id, pageable);
		files.setCategory(category.getCategory());
		files.setId(category.getId());
		files.setListFile(page.getContent());
		return files;
	}
}

