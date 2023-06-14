package com.phuclq.student.repository;

import com.phuclq.student.domain.Category;
import com.phuclq.student.dto.CategoryFilePageDTO;
import com.phuclq.student.dto.CategoryHomeFileResult;
import com.phuclq.student.dto.CategoryHomeResult;

import com.phuclq.student.dto.FileHomeDoFilterDTO;
import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    String sql = " ";



    Page<Category> findAll(Pageable pageable);

    Category findAllById(int id);

    Page<Category> findAllByIdIn(List<Integer> id,Pageable pageable);

    @Query(value = "select category.id as id,category.category as name, file.count as countCategory   from category left join (select category_id,count(*) as count from file f join category c on f.category_id = c.id  join file_price fp on f.id = fp.file_id  join industry i on f.industry_id = i.id join attachment a on f.id = a.request_id "
        + " and a.file_type ='FILE_UPLOAD' inner join user u on f.author_id = u.id where f.approver_id is not null and f.is_deleted =0 group by category_id) file on category.id = file.category_id ", nativeQuery = true)
    List<CategoryHomeResult> getCategoriesHome();

    @Query("SELECT distinct c.id as id,c.category as name FROM Category c join File f on f.categoryId = c.id inner join FilePrice fp on f.id = fp.fileId  join Industry i on f.industryId = i.id  join ATTACHMENT a on f.id = a.requestId and a.fileType = 'FILE_AVATAR' join User u on f.authorId = u.id WHERE f.isDeleted = false and  c.id in ?1")
    Page<CategoryFilePageDTO> findAllByIdInFile(List<Integer> id,Pageable pageable);


    @Query("SELECT distinct c.id as id,c.category as name FROM Category c join File f on f.categoryId = c.id inner join FilePrice fp on f.id = fp.fileId  join Industry i on f.industryId = i.id  join ATTACHMENT a on f.id = a.requestId and a.fileType = 'FILE_AVATAR' join User u on f.authorId = u.id WHERE f.isDeleted = false ")
    Page<CategoryFilePageDTO> findAllByIdInFile(Pageable pageable);

}
