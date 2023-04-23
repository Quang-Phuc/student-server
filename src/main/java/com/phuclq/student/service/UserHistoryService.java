package com.phuclq.student.service;

import com.phuclq.student.domain.UserHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserHistoryService {
    UserHistory activateFileHistory (Integer userId, Integer fileId, Integer activityId);

    Page<UserHistory> getAllActivitiesByUser(Integer userId, Pageable pageable);

    Page<UserHistory> getAllActivitiesByUserAndActivity(Integer userId, Integer activityId, Pageable pageable);
    
    void deleteActivityByUser(Integer userId, Integer fileId, Integer activityId);
    
    UserHistory saveUserHistory(UserHistory userHistory);
    
    UserHistory handlePayment(String orderId, Long amount);

    UserHistory handleDownloadPayment(Integer fileId, Integer userId);
}
