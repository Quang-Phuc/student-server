package com.phuclq.student.controller;

import com.phuclq.student.dto.AttachmentDTO;
import com.phuclq.student.dto.CategoryHomeFileResult;
import com.phuclq.student.dto.DownloadFileDTO;
import com.phuclq.student.dto.FileHomePageRequest;
import com.phuclq.student.dto.FileResultDto;
import com.phuclq.student.service.AttachmentService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import com.phuclq.student.domain.UserHistory;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.CategoryHomeDTO;
import com.phuclq.student.dto.FileApprove;
import com.phuclq.student.dto.FileDTO;
import com.phuclq.student.dto.FileResult;
import com.phuclq.student.dto.FileSearchRequest;
import com.phuclq.student.dto.FileUploadRequest;
import com.phuclq.student.kafka.MessageInfoEvent;
import com.phuclq.student.kafka.MessageInfoService;
import com.phuclq.student.service.ConfirmationTokenService;
import com.phuclq.student.service.EmailSenderService;
import com.phuclq.student.service.FileService;
import com.phuclq.student.service.GoogleCloudService;
import com.phuclq.student.service.UserHistoryService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.ActivityConstants;
import com.phuclq.student.utils.PaginationUtil;

@RestController
@RequestMapping("/api")
public class FileController {

  @Autowired
  private FileService fileService;

  @Autowired
  private GoogleCloudService gcpService;

  @Autowired
  private UserHistoryService userHistoryService;

  @Autowired
  private UserService userService;
  @Autowired
  private RestEntityResponse restEntityRes;

  @Autowired
  private MessageInfoService messageInfoService;

  @Autowired
  private ConfirmationTokenService confirmationTokenService;

  @Autowired
  private EmailSenderService emailSenderService;

  @Autowired
  private AttachmentService attachmentService;

  @GetMapping("/file")
  public ResponseEntity<List<File>> getFilesByCategory(@PathParam("categoryId") Integer categoryId,
      Pageable pageable) {
    Page<File> page = fileService.findFilesByCategory(categoryId, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/file/{id}")
  public ResponseEntity<FileDTO> getFile(@PathVariable("id") Integer id) throws Exception {
    if (id == null) {
      throw new Exception("id must not null!");
    }
    FileDTO fileDTO = fileService.getFile(id);
    return ResponseEntity.ok().body(fileDTO);
  }

  @PostMapping("/file/search")
  public ResponseEntity<List<File>> searchFile(@RequestBody FileSearchRequest fileSearchRequest,
      Pageable pageable) {
    Page<File> page = fileService.searchFiles(fileSearchRequest.getCategory(),
        fileSearchRequest.getSpecialization(), fileSearchRequest.getSchool(),
        fileSearchRequest.getTitle(), fileSearchRequest.getIsVip(), fileSearchRequest.getPrice(),
        pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @PostMapping("/file/upload")
  public ResponseEntity<File> uploadFile(@RequestBody FileUploadRequest fileUploadRequest)
      throws Exception {
    File file = fileService.uploadFile(fileUploadRequest);
    return ResponseEntity.ok(file);
  }




  @GetMapping("/file/download-confirm/{code}")
  public ResponseEntity<?> downloadFile(@PathVariable("code") String code) throws Exception {
    User user = userService.getUserLogin();
    File fileDownload = fileService.getFileDownload(code);
    String filePath = "fileDownload.getFile()";
    String[] filePathArr = filePath.split("/");
    String fileName = filePathArr[filePathArr.length - 1];
    java.io.File file = new java.io.File("fileDownload.getFile()");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
    Path path = Paths.get(file.getAbsolutePath());
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
    userHistoryService.handleDownloadPayment(fileDownload.getId(), user.getId());
    MessageInfoEvent message = new MessageInfoEvent(fileDownload.getAuthorId(),
        user.getUserName() + " đã download tài liệu");
    //messageInfoService.sendMessage("test", message);
    return ResponseEntity.ok().headers(headers).contentLength(file.length())
        .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
  }

  @PostMapping("/file/download")
  public ResponseEntity<String> downloadDocument(@RequestBody DownloadFileDTO downloadFileDTO)
      throws DocumentException, com.itextpdf.text.DocumentException, IOException {

    AttachmentDTO file = fileService.downloadDocument(downloadFileDTO);

    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(file).getResponse();
  }

  @GetMapping("/file/{fileId}/report")
  public ResponseEntity<String> reportDocument(@PathVariable("fileId") Integer fileId)
      throws Exception {
    User user = userService.getUserLogin();
    UserHistory userHistory = userHistoryService.activateFileHistory(user.getId(), fileId,
        ActivityConstants.REPORT);
    String result = "";
    HttpStatus status;
    if (userHistory == null) {
      status = HttpStatus.ACCEPTED;
      result = "Tài liệu này đã được báo cáo";
    } else {
      status = HttpStatus.OK;
      result = "Báo cáo vi phạm thành công";
    }
    return restEntityRes.setHttpStatus(status).setDataResponse(result).getResponse();
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/file/{fileId}/like")
  public ResponseEntity<String> likeDocument(@PathVariable("fileId") Integer fileId) {
    String result;
    HttpStatus status;
    User user = userService.getUserLogin();
    UserHistory historyFile = userHistoryService.activateFileHistory(user.getId(), fileId,
        ActivityConstants.LIKE);
    if (historyFile == null) {
      status = HttpStatus.ACCEPTED;
      result = "Tài liệu đã được yêu thích";
    } else {
      status = HttpStatus.OK;
      result = "Tài liệu đã được đưa vào danh sách yêu thích";
    }
    return restEntityRes.setHttpStatus(status).setDataResponse(result).getResponse();
  }

  @DeleteMapping("/file/{fileId}/unlike")
  public ResponseEntity<?> unLikeDocument(@PathVariable("fileId") Integer fileId) {
    User user = userService.getUserLogin();
    userHistoryService.deleteActivityByUser(user.getId(), fileId, ActivityConstants.LIKE);
    String result = "Tài liệu đã được loại bỏ khỏi danh sách yêu thích";
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/file/{fileId}/card")
  public ResponseEntity<String> cardDocument(@PathVariable("fileId") Integer fileId) {
    String result;
    HttpStatus status;
    User user = userService.getUserLogin();
    UserHistory historyFile = userHistoryService.activateFileHistory(user.getId(), fileId,
        ActivityConstants.CARD);
    if (historyFile == null) {
      status = HttpStatus.ACCEPTED;
      result = "Tài liệu đã được thêm vào giỏ hàng ";
    } else {
      status = HttpStatus.OK;
      result = "Tài liệu đã được thêm vào giỏ hàn";
    }
    return restEntityRes.setHttpStatus(status).setDataResponse(result).getResponse();
  }

  @CrossOrigin(origins = "*")
  @DeleteMapping("/file/{fileId}/uncard")
  public ResponseEntity<?> unCardDocument(@PathVariable("fileId") Integer fileId) {
    User user = userService.getUserLogin();
    userHistoryService.deleteActivityByUser(user.getId(), fileId, ActivityConstants.CARD);
    String result = "Tài liệu đã được xóa vào giỏ hàng ";
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/file/category/suggest")
  public ResponseEntity<List<File>> getSuggestByCategory(
      @PathParam("categoryId") Integer categoryId) {
    List<String> strings = Arrays.asList("Tài liệu học tập", "Bài tập cuối kỳ");
    HttpStatus status = HttpStatus.OK;
    return restEntityRes.setHttpStatus(status).setDataResponse(strings).getResponse();
  }

  @GetMapping("/file/category/home")
  public ResponseEntity<?> findCategoriesHome() {
    List<CategoryHomeDTO> categoryHomeDTOList = fileService.getCategoriesHome();
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(categoryHomeDTOList)
        .getResponse();
  }

  @PostMapping("/file/page-home")
  public ResponseEntity<?> fileHomePage(@RequestBody FileHomePageRequest request) {
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    CategoryHomeFileResult result = fileService.filesPage(request, pageable);
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
  }

  @PostMapping("/file/category/search")
  public ResponseEntity<?> searchFileByCategory(@RequestBody FileHomePageRequest request) {
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
    FileResultDto result = fileService.searchFileCategory(request, request.getCategoryId(),
        pageable);
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
  }

  @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINSYSTEM')")
  @GetMapping("/file/approve")
  public ResponseEntity<?> getFileUnApprove(Pageable pageable) {
    Page<FileApprove> page = fileService.getFileUnApprove(pageable);
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(page).getResponse();
  }

  @GetMapping("/file/top8")
  public ResponseEntity<?> findTop10OrderByIdDesc() {
    List<File> files = fileService.findTop8FileOrderByIdDesc();
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(files).getResponse();
  }

  @GetMapping("/file/downloaded")
  public ResponseEntity<?> getFileDownloaded(Pageable pageable) {
    User user = userService.getUserLogin();
    Page<FileResult> result = fileService.searchfileDownloaded(user.getId(), pageable);
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
  }

  @GetMapping("/file/downloadS3-upload")
  public ResponseEntity<List<File>> downloadS3(@RequestParam Long id,@RequestParam  String fileType, HttpServletRequest request)
      throws IOException {
    AttachmentDTO attachmentByIdFromS3 = attachmentService.getAttachmentByIdFromS3Update(id,fileType, request);

    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(attachmentByIdFromS3)
        .getResponse();
  }

  @GetMapping("/file/downloadS3-request")
  public ResponseEntity<List<File>> downloadS3Request(@RequestParam Integer id,@RequestParam  String fileType, HttpServletRequest request)
      throws IOException {
    List<AttachmentDTO> attachmentByIdFromS3 = attachmentService.getAttachmentByRequestIdFromS3AndTypes(
        id,Arrays.asList(fileType));

    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(attachmentByIdFromS3)
        .getResponse();
  }

}
