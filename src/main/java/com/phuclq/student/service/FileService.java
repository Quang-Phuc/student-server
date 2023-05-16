package com.phuclq.student.service;

import com.google.cloud.storage.Storage;
import com.phuclq.student.controller.FileController.FileHomePageRequest;
import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.dto.FileApprove;
import com.phuclq.student.dto.FileDTO;
import com.phuclq.student.dto.FileHomeDoFilterDTO;
import com.phuclq.student.dto.FileResult;
import com.phuclq.student.dto.FileUploadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    Page<File> findFilesByCategory(Integer categoryId, Pageable pageable);

    FileDTO getFile(Integer id);

    Page<File> searchFiles(Integer category, Integer specialization, Integer school, String title, Boolean isVip, Float price, Pageable pageable);

    File uploadFile(FileUploadRequest fileUploadRequest) throws IOException;

    boolean registryFileVip(File file);

    File downloadDocument(Integer fileId, User user);

    void approverFile(Integer approverId, Integer id, MultipartFile fileCut, Storage storage, String bucketName) throws IOException;

    public List<CategoryHomeDTO> getCategoriesHome() ;
    
    File updateFile(FileUploadRequest fileUploadRequest, Storage storage, String bucketName) throws IOException;
    Page<FileHomeDoFilterDTO> filesPage(FileHomePageRequest request,Pageable pageable);
    List<FileResult> searchfileCategory(FileHomePageRequest request, Integer categoryId,Pageable pageable);
    
    Page<FileApprove> getFileUnApprove(Pageable pageable);
    
    File getFileDownload(String fileHashcode);

    List<File> findTop8FileOrderByIdDesc();
    
    Page<FileResult> searchfileDownloaded(Integer userId, Pageable pageable);
}
