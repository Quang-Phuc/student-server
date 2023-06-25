package com.phuclq.student.controller.admin;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.AdminRoleDTO;
import com.phuclq.student.dto.FileApproveRequest;
import com.phuclq.student.dto.UserAccountDTO;
import com.phuclq.student.dto.UserDTO;
import com.phuclq.student.service.FileService;
import com.phuclq.student.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminFileController {

  @Autowired
  private FileService fileService;

  @Autowired
  private RestEntityResponse restEntityRes;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/file/approve")
  public ResponseEntity<?> approveFile(FileApproveRequest fileApprove) throws IOException {
    fileService.approveFile(fileApprove.getId());
    String result = "approve success";
    return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(result).getResponse();
  }

}
