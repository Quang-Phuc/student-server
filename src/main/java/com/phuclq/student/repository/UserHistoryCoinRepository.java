package com.phuclq.student.repository;

import com.phuclq.student.domain.UserHistoryCoin;
import com.phuclq.student.dto.UserHistoryCoinResult;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryCoinRepository extends JpaRepository<UserHistoryCoin, Integer> {

//	Page<UserHistoryCoin> findByUserId(Integer userId, Pageable pageable);
//
//	Page<UserHistoryCoin> findByTransactionAndUserId(Integer transaction, Integer userId, Pageable pageable);
//
//	@Query(value = "select * from user_history_coin where transaction = ?1 and user_id = ?2 and activity_date between ?3 and ?4 ", nativeQuery = true)
//	Page<UserHistoryCoin> findByActiveDate(Integer userId, Timestamp dateFrom, Timestamp dateTo, Pageable pageable);
//
//	@Query(value = "select * from user_history_coin where transaction = ?1 and user_id = ?2 and activity_date between ?3 and ?4 ", nativeQuery = true)
//	Page<UserHistoryCoin> findByTransactionAndActiveDate(Integer transaction, Integer userId, Timestamp dateFrom,
//			Timestamp dateTo, Pageable pageable);
//
//	@Query(value = "select uhc.*, u.user_name as user_name, u.email as email from user_history_coin uhc right join user u on u.id = uhc.user_id "
//			+ " where uhc.id is not null and uhc.transaction = ?1 and u.email like concat('%',?2,'%')", nativeQuery = true)
//	Page<UserHistoryCoinResult> getManagerTransaction(Integer transaction, String email, Pageable pageable);
//
//	@Query(value = "select uhc.*, u.user_name as user_name, u.email as email from user_history_coin uhc right join user u on u.id = uhc.user_id"
//			+ " where uhc.id is not null and u.email like concat('%',?1,'%')", nativeQuery = true)
//	Page<UserHistoryCoinResult> getManagerTransaction(String email, Pageable pageable);
//
//	@Query(value = "select uhc.*, u.user_name as user_name, u.email as email from user_history_coin uhc right join user u on u.id = uhc.user_id "
//			+ " where uhc.id is not null and uhc.transaction = ?1 and uhc.activity_date between ?2 and ?3 and u.email like concat('%',?4,'%') ", nativeQuery = true)
//	Page<UserHistoryCoinResult> getManagerTransaction(Integer transaction, Timestamp dateFrom, Timestamp dateTo,
//			String email, Pageable pageable);
//
//	@Query(value = "select uhc.*, u.user_name as user_name, u.email as email from user_history_coin uhc right join user u on u.id = uhc.user_id "
//			+ " where uhc.id is not null and uhc.activity_date between ?1 and ?2 and u.email like concat('%',?3,'%')", nativeQuery = true)
//	Page<UserHistoryCoinResult> getManagerTransaction(Timestamp dateFrom, Timestamp dateTo, String email, Pageable pageable);

}
