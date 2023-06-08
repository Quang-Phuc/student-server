package com.phuclq.student.repository;

import com.phuclq.student.domain.Category;
import com.phuclq.student.dto.CategoryHomeFileResult;
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

    @Query(value = "select category.id as id,category.category as name, file.count as countCategory   from category left join (select category_id,count(*) as count from file f inner join file_price fp on f.id = fp.file_id inner join industry i on f.industry_id = i.id inner join user u on f.author_id = u.id where approver_id is not null group by category_id) file on category.id = file.category_id ", nativeQuery = true)
    List<CategoryHomeResult> getCategoriesHome();

    @Query(value = "select category.id as id,category.category as name   from category WHERE category.id in ?1 ORDER BY ?#{#pageable}",
        countQuery = "select category.id as id,category.category as name  from category WHERE category.id in ?1",
        nativeQuery = true)
    Page<CategoryHomeFileResult> findAllByIdInFile(List<Integer> id,Pageable pageable);

    @Query(value = "select category.id as id,category.category as name   from category  ORDER BY ?#{#pageable}",
        countQuery = "select category.id as id,category.category as name   from category ",
        nativeQuery = true)
    Page<CategoryHomeFileResult> findAllByIdInFile(Pageable pageable);

}
