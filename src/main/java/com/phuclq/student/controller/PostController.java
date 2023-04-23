package com.phuclq.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.File;
import com.phuclq.student.service.PostService;

import lombok.Data;

@RestController
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
    private RestEntityResponse restEntityRes;
	
	@PutMapping("/api/approval-post")
	public void approvalPost(Integer id) {
		postService.approvalPost(id);
	}
	
	@PutMapping("/api/delete-post")
	public void deletePost(Integer id) {
		postService.deletePost(id);
	}
	
	@PostMapping("/api/get-post")
	public ResponseEntity<?> getPost(@RequestBody RequestPost requestPost, Pageable pageable){
		Page<File> page = postService.searchPost(requestPost, pageable);
		return ResponseEntity.ok(page);
	}
	
	@Data
	public static class RequestPost{
		private String createDateFrom;
		private String createDateTo;
		private String title;
	}
}
