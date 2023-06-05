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

    Page<Category> findAllByIdIn(List<Integer> id,Pageable pageable);

    @Query(value = "select category.id as id,category.category as name, file.count as countCategory   from category left join (select category_id,count(*) as count from file group by category_id) file on category.id = file.category_id ", nativeQuery = true)
    List<CategoryHomeResult> getCategoriesHome();

}
