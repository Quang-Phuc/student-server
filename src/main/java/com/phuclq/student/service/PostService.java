package com.phuclq.student.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.phuclq.student.controller.PostController.RequestPost;
import com.phuclq.student.domain.File;

public interface PostService {
	
	public void approvalPost(Integer id);
	
	public void deletePost(Integer id);
	
	public Page<File> searchPost(RequestPost requestPost,Pageable pageable);
}
