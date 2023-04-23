package com.phuclq.student.repository;

import com.phuclq.student.domain.Category;
import com.phuclq.student.dto.CategoryHomeResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findAll(Pageable pageable);

    Category findAllById(int id);

    @Query(value = "select id as id , category as name , count(category) as countCategory   from category group by category,Id", nativeQuery = true)
    List<CategoryHomeResult> getCategoriesHome();

}
