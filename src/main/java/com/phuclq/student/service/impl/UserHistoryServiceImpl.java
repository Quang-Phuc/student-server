package com.phuclq.student.service.impl;

import com.phuclq.student.domain.File;
import com.phuclq.student.domain.UserCoin;
import com.phuclq.student.domain.UserHistory;
import com.phuclq.student.domain.UserHistoryFile;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.repository.UserCoinRepository;
import com.phuclq.student.repository.UserHistoryFileRepository;
import com.phuclq.student.repository.UserHistoryRepository;
import com.phuclq.student.service.UserHistoryService;
import com.phuclq.student.utils.ActivityConstants;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
	@Autowired
	private UserHistoryRepository userHistoryRepository;

	@Autowired
	private UserHistoryFileRepository userHistoryFileRepository;
	
	@Autowired
	private UserCoinRepository coinRepository;
	@Autowired
	private FileRepository fileRepository;

	@Override
	public UserHistory activateFileHistory(Integer userId, Integer fileId, Integer activityId) {
		List<UserHistoryFile> fileHistory = userHistoryFileRepository.findFileHistory(activityId, fileId, userId);
		if (fileHistory.size() > 0) {
			return null;
		}
		UserHistory userHistory = new UserHistory(userId, activityId);
		UserHistory saveUserHistory = userHistoryRepository.save(userHistory);
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		UserHistoryFile userHistoryFile = new UserHistoryFile(saveUserHistory.getId(), fileId, timestamp);
		userHistoryFileRepository.save(userHistoryFile);
		File file = fileRepository.findById(fileId).get();
		if(activityId.equals(ActivityConstants.DOWNLOAD)){
			file.setDowloading(Objects.nonNull(file.getDowloading())?file.getDowloading()+1:1);
		}
		if(activityId.equals(ActivityConstants.CARD)){
			file.setTotalCard(Objects.nonNull(file.getTotalCard())?file.getTotalCard()+1:1);
		}
		if(activityId.equals(ActivityConstants.LIKE)){
			file.setTotalLike(Objects.nonNull(file.getTotalLike())?file.getTotalLike()+1:1);
		}
		fileRepository.save(file);


		return saveUserHistory;
	}

	@Override
	public Page<UserHistory> getAllActivitiesByUser(Integer userId, Pageable pageable) {
		return userHistoryRepository.findUserHistoriesByUserId(userId, pageable);
	}

	@Override
	public Page<UserHistory> getAllActivitiesByUserAndActivity(Integer userId, Integer activityId, Pageable pageable) {
		return userHistoryRepository.findUserHistoriesByUserIdAndActivityId(userId, activityId, pageable);
	}

	@Override
	public void deleteActivityByUser(Integer userId, Integer fileId, Integer activityId) {
		List<UserHistoryFile> fileHistory = userHistoryFileRepository.findFileHistory(activityId, fileId, userId);
		if (fileHistory.size() > 0) {
			Optional<UserHistory> history = userHistoryRepository.findById(fileHistory.get(0).getUserHisotyId());
			userHistoryFileRepository.delete(fileHistory.get(0));
			userHistoryRepository.delete(history.get());
		}
		File file = fileRepository.findById(fileId).get();
		if(activityId.equals(ActivityConstants.DOWNLOAD)){
			file.setDowloading(Objects.nonNull(file.getDowloading())?file.getDowloading()-1:0);
		}
		if(activityId.equals(ActivityConstants.CARD)){
			//.setTotalCard(Objects.nonNull(file.getTotalCard())?file.getTotalCard()-1:0);
		}
		if(activityId.equals(ActivityConstants.LIKE)){
			file.setTotalLike(Objects.nonNull(file.getTotalLike())?file.getTotalLike()-1:0);
		}
		fileRepository.save(file);
	}

	@Override
	public UserHistory saveUserHistory(UserHistory userHistory) {
		// TODO Auto-generated method stub
		return userHistoryRepository.save(userHistory);
	}

	@Override
	public UserHistory handlePayment(String orderId, Long amount) {
		List<UserHistory> lst = userHistoryRepository.findByOrderId(orderId);
		if(!lst.isEmpty()) {
			UserHistory userHistory = lst.get(0);
			userHistory.setStatus(2);
			userHistoryRepository.save(userHistory);
			UserCoin coin = coinRepository.findByUserId(userHistory.getUserId());
			coin.setTotalCoin(coin.getTotalCoin() - amount);
			coinRepository.save(coin);
			return userHistory;
		} else {
			return null;
		}
		
	}

	@Override
	public UserHistory handleDownloadPayment(Integer fileId, Integer userId) {
		List<UserHistoryFile> historyFileList = userHistoryFileRepository.findFileHistory(ActivityConstants.DOWNLOAD, fileId, userId);
		if (!historyFileList.isEmpty()) {
			UserHistoryFile userHistoryFile = historyFileList.get(0);
			Optional<UserHistory> userHistoryOptional = userHistoryRepository.findById(userHistoryFile.getUserHisotyId());
			if (userHistoryOptional.isPresent()) {
				UserHistory userHistory = userHistoryOptional.get();
				userHistory.setStatus(2);
				UserHistory result = userHistoryRepository.save(userHistory);
				return result;
			}
		}
		return null;
	}

}
