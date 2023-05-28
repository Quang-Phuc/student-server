package com.phuclq.student.service;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface S3StorageService {
  String uploadFileToS3(MultipartFile file) throws IOException;

  String uploadFileToS3ByBase64(String base64String, String contentType, String fileName)
          throws IOException, NoSuchAlgorithmException;

  String downloadFileFromS3(String fileName) throws IOException;

  List<Bucket> showListBucket();
}
