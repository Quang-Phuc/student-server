package com.phuclq.student.controller;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.ChangePasswordDTO;
import com.phuclq.student.dto.UserAccountDTO;
import com.phuclq.student.dto.UserDTO;
import com.phuclq.student.dto.UsersSearchRequest;
import com.phuclq.student.service.ConfirmationTokenService;
import com.phuclq.student.service.EmailSenderService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailSenderService emailSenderService;
    
    @SuppressWarnings("rawtypes")
	@Autowired
    private RestEntityResponse restEntityRes;

    @PostMapping("/register")
    public ResponseEntity<?> registryUser(@RequestBody @Valid UserAccountDTO accountDTO) {

	        User user = userService.registryUser(accountDTO);
	        SimpleMailMessage mailMessage = confirmationTokenService.sendEmail(user);
	      //  emailSenderService.sendEmail(mailMessage);
	        return restEntityRes.setHttpStatus(HttpStatus.CREATED).setDataResponse(user.getUserName()).getResponse();
        }


    @GetMapping("activate-account")
    public ResponseEntity<User> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        User user = confirmationTokenService.confirmUserAccount(confirmationToken);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(user).getResponse();
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser(Pageable pageable) {
    	Page<UserDTO> list = userService.getUser(pageable);
    	return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(list).getResponse();
    }

    @GetMapping("/user/{id}/files")
    public ResponseEntity<List<File>> getFilesByUser(@PathVariable("id") Integer id, Pageable pageable) {
        Page<File> page = userService.getListFileByUser(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/forgot-pass")
    public ResponseEntity<?> changePassword(@Param("email") String email) {
        userService.forgotPassword(email);
        String message = "Mật khẩu mới đã được gửi về email của bạn";
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(message).getResponse();
    }
    

    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        User user = userService.changePassword(changePasswordDTO.getPassword(), changePasswordDTO.getPasswordNew(), changePasswordDTO.getPasswordConfirm());
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(user).getResponse();
    }

    @PostMapping("/search/users")
    public ResponseEntity<?> findUserByUserNameAndEmail(UsersSearchRequest usersSearchRequest, Pageable pageable) {
        Page<User> page = userService.findUserByUserNameAndEmailAndPhone(usersSearchRequest, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(),
                page);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setHttpHeaders(httpHeaders).setDataResponse(page.getContent()).getResponse();
    }

    @GetMapping("/user/get-login")
    public ResponseEntity<UserDTO> getUserLogin() {
        UserDTO user = userService.getUserResultLogin();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/user/gettop")
    public ResponseEntity<?>  findTop10OrderByIdDesc() {
        List<User> data = userService.findTop10OrderByIdDesc();
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(data).getResponse();

    }

}
