package com.phuclq.student.service;

import com.phuclq.student.domain.File;
import com.phuclq.student.dto.AttachmentDTO;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.dto.CategoryHomeFileResult;
import com.phuclq.student.dto.DownloadFileDTO;
import com.phuclq.student.dto.FileApprove;
import com.phuclq.student.dto.FileDTO;
import com.phuclq.student.dto.FileHomePageRequest;
import com.phuclq.student.dto.FileResult;
import com.phuclq.student.dto.FileResultDto;
import com.phuclq.student.dto.FileUploadRequest;
import org.dom4j.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface FileService {

  Page<File> findFilesByCategory(Integer categoryId, Pageable pageable);

  FileDTO getFile(Integer id);

  Page<File> searchFiles(Integer category, Integer specialization, Integer school, String title,
      Boolean isVip, Float price, Pageable pageable);

  File uploadFile(FileUploadRequest fileUploadRequest)
      throws IOException, com.itextpdf.text.DocumentException;

  boolean registryFileVip(Integer userId);

  List<AttachmentDTO> downloadDocument(DownloadFileDTO downloadFileDTO)
      throws DocumentException, com.itextpdf.text.DocumentException, IOException;

  void approveFile( Integer id) throws IOException;

  List<CategoryHomeDTO> getCategoriesHome();

  CategoryHomeFileResult filesPage(FileHomePageRequest request, Pageable pageable);

  FileResultDto searchFileCategory(FileHomePageRequest request, Integer categoryId,
      Pageable pageable);

  Page<FileApprove> getFileUnApprove(Pageable pageable);

  File getFileDownload(String fileHashcode);

  List<File> findTop8FileOrderByIdDesc();

  Page<FileResult> searchfileDownloaded(Integer userId, Pageable pageable);
}
